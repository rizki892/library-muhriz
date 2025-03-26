package com.mini.project.library_muhriz.repositories;

import com.mini.project.library_muhriz.models.Book;
import com.mini.project.library_muhriz.models.Loan;
import com.mini.project.library_muhriz.models.LoanStatus;
import com.mini.project.library_muhriz.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LoanRepository extends JpaRepository<Loan, UUID> {
    Optional<Loan> findByUserAndStatus(User user, LoanStatus status);

    @Query("SELECT l FROM Loan l WHERE l.status = 'ACCEPTED' AND l.dueDate < :today")
    List<Loan> findOverdueLoans(LocalDate today);

    @Query("SELECT l FROM Loan l WHERE l.status = 'ACCEPTED'")
    List<Loan> findActiveLoans();

    Loan findByBookAndStatusIn(Book book, List<LoanStatus> statusList);
}

