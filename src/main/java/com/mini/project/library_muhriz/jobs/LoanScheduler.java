package com.mini.project.library_muhriz.jobs;

import com.mini.project.library_muhriz.models.Loan;
import com.mini.project.library_muhriz.models.LoanStatus;
import com.mini.project.library_muhriz.repositories.LoanRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@Component
public class LoanScheduler {
    private final LoanRepository loanRepository;

    public LoanScheduler(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    // 🔥 Cek setiap hari jam 00:00 (tengah malam) untuk mendeteksi peminjaman yang telat
    @Scheduled(cron = "0 0 0 * * *") // Atau bisa pakai @Scheduled(fixedRate = 86400000) untuk setiap 24 jam
    public void checkOverdueLoans() {
        LocalDate today = LocalDate.now();
        log.info("Menjalankan pengecekan keterlambatan peminjaman pada {}", today);

        // Cari semua peminjaman yang masih "ACCEPTED" tapi sudah melewati dueDate
        List<Loan> overdueLoans = loanRepository.findByStatusAndDueDateBefore(LoanStatus.ACCEPTED, today);

        if (overdueLoans.isEmpty()) {
            log.info("Tidak ada peminjaman yang terlambat hari ini.");
            return;
        }

        for (Loan loan : overdueLoans) {
            loan.setStatus(LoanStatus.OVERDUE); // Ubah status menjadi OVERDUE
            loanRepository.save(loan);
            log.warn("Buku '{}' milik '{}' telah melewati batas pengembalian!",
                    loan.getBook().getTitle(), loan.getUser().getName());
        }

        log.info("Total peminjaman yang telat dikembalikan hari ini: {}", overdueLoans.size());
    }
}
