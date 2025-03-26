package com.mini.project.library_muhriz.services;

import com.mini.project.library_muhriz.dto.request.LoanRequest;
import com.mini.project.library_muhriz.models.*;
import com.mini.project.library_muhriz.repositories.BookRepository;
import com.mini.project.library_muhriz.repositories.LoanRepository;
import com.mini.project.library_muhriz.repositories.UserRepository;
import com.mini.project.library_muhriz.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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




    public List<Loan> getOverdueLoans() {
        return loanRepository.findOverdueLoans(LocalDate.now());
    }

    public List<Loan> getActiveLoans() {
        return loanRepository.findActiveLoans();
    }
}
