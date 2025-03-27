package com.mini.project.library_muhriz.services;

import com.mini.project.library_muhriz.dto.request.LoanRequest;
import com.mini.project.library_muhriz.dto.response.LoanResponse;
import com.mini.project.library_muhriz.models.*;
import com.mini.project.library_muhriz.repositories.BookRepository;
import com.mini.project.library_muhriz.repositories.LoanRepository;
import com.mini.project.library_muhriz.repositories.UserRepository;
import com.mini.project.library_muhriz.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class LoanService {
    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private JwtUtil jwtUtil;

    public String requestLoan(LoanRequest request) {
        String email = jwtUtil.getCurrentUserEmail();
        if (email == null) {
            return "User tidak terautentikasi";
        }

        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()) {
            return "User tidak ditemukan";
        }
        User user = userOpt.get();

        Optional<Book> bookOpt = bookRepository.findById(request.getBookId());
        if (bookOpt.isEmpty()) {
            return "Buku tidak ditemukan";
        }
        Book book = bookOpt.get();

        if (book.getDeletedAt() != null) {
            return "Buku ini telah dihapus dan tidak bisa dipinjam";
        }

        // Periksa apakah buku sedang dalam peminjaman dengan status PENDING atau ACCEPTED
        Loan existingLoan = loanRepository.findByBookAndStatusIn(book, Arrays.asList(LoanStatus.PENDING, LoanStatus.ACCEPTED));
        if (existingLoan != null) {
            return "Buku ini sedang dalam proses peminjaman oleh pengguna lain.";
        }

        // Buat peminjaman baru
        Loan loan = new Loan();
        loan.setUser(user);
        loan.setBook(book);
        loan.setLoanDate(LocalDate.now());
        loan.setDueDate(LocalDate.now().plusWeeks(2));
        loan.setStatus(LoanStatus.PENDING);

        loanRepository.save(loan);
        return "Pengajuan peminjaman berhasil, menunggu persetujuan admin";
    }
    public String acceptLoan(LoanRequest loanRequest) {
        String email = jwtUtil.getCurrentUserEmail();

        // Mengambil user dari email yang sedang login
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NoSuchElementException("User dengan email " + email + " tidak ditemukan"));

        // Mengambil buku berdasarkan ID dari loanRequest
        Book book = bookRepository.findById(loanRequest.getBookId())
                .orElseThrow(() -> new NoSuchElementException("Buku dengan ID " + loanRequest.getBookId() + " tidak ditemukan"));

        // Jika buku sudah dihapus, lemparkan exception
        if (book.getDeletedAt() != null) {
            throw new IllegalStateException("Buku '" + book.getTitle() + "' telah dihapus dan tidak bisa dipinjam");
        }

        // Mengecek apakah ada peminjaman yang masih dalam status PENDING
        Loan loan = loanRepository.findByBookAndUserAndStatus(book,user, LoanStatus.PENDING);
        if (loan == null) {
            throw new NoSuchElementException("Data peminjaman untuk buku '" + book.getTitle() + "' tidak ditemukan");
        }

        // Mengubah status peminjaman menjadi ACCEPTED
        loan.setStatus(LoanStatus.ACCEPTED);
        loanRepository.save(loan);

        return String.format("Peminjaman buku '%s' oleh %s telah diterima.", book.getTitle(), user.getName());
    }
    public String returnLoan(LoanRequest loanRequest) {
        String email = jwtUtil.getCurrentUserEmail();

        //  Ambil user berdasarkan email
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NoSuchElementException("User tidak ditemukan"));

        //  Ambil buku berdasarkan ID
        Book book = bookRepository.findById(loanRequest.getBookId())
                .orElseThrow(() -> new NoSuchElementException("Buku tidak ditemukan"));

        // Jika buku sudah dihapus, lempar exception
        if (book.getDeletedAt() != null) {
            throw new IllegalStateException("Buku '" + book.getTitle() + "' telah dihapus dan tidak bisa dipinjam");
        }

        // Cari peminjaman dengan status ACCEPTED
        Loan loan = loanRepository.findByBookAndUserAndStatus(book,user,LoanStatus.ACCEPTED);
        if (loan == null) {
            throw new NoSuchElementException("Tidak ada peminjaman aktif untuk buku '" + book.getTitle() + "'");
        }

        //  Jika buku belum dikonfirmasi admin (masih PENDING), tidak bisa dikembalikan
        if (loan.getStatus() == LoanStatus.PENDING) {
            throw new IllegalStateException("Buku '" + book.getTitle() + "' masih dalam status PENDING, belum bisa dikembalikan.");
        }

        //  Ubah status menjadi RETURNED
        loan.setStatus(LoanStatus.RETURNED);
        loan.setReturnDate(LocalDate.now());
        loanRepository.save(loan);

        return String.format("Buku '%s' telah dikembalikan oleh %s.", book.getTitle(), user.getName());
    }


    public List<Loan> getOverdueLoans() {
        return loanRepository.findOverdueLoans(LocalDate.now());
    }

    public List<Loan> getActiveLoans() {
        return loanRepository.findActiveLoans();
    }
}
