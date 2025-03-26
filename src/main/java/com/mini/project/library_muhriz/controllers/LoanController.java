package com.mini.project.library_muhriz.controllers;

import com.mini.project.library_muhriz.dto.request.LoanRequest;
import com.mini.project.library_muhriz.dto.response.LoanResponse;
import com.mini.project.library_muhriz.models.Loan;
import com.mini.project.library_muhriz.services.LoanService;
import com.mini.project.library_muhriz.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/loans")
@Validated
public class LoanController {
    @Autowired
    private LoanService loanService;

    @PostMapping("/request")
    public ResponseEntity<ApiResponse<String>> requestLoan(@Valid @RequestBody LoanRequest request) {
        return ResponseEntity.ok(ApiResponse.success(loanService.requestLoan(request)));
    }

    @GetMapping("/overdue")
    public ResponseEntity<ApiResponse<List<LoanResponse>>> getOverdueLoans() {
        List<LoanResponse> overdueLoans = loanService.getOverdueLoans()
                .stream()
                .map(this::convertToLoanResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success("Daftar buku yang telat dikembalikan", overdueLoans));
    }

    @GetMapping("/active")
    public ResponseEntity<ApiResponse<List<LoanResponse>>> getActiveLoans() {
        List<LoanResponse> activeLoans = loanService.getActiveLoans()
                .stream()
                .map(this::convertToLoanResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success("Daftar buku yang sedang dipinjam", activeLoans));
    }

    private LoanResponse convertToLoanResponse(Loan loan) {
        return new LoanResponse(
                loan.getId(),
                loan.getUser().getName(),
                loan.getBook().getTitle(),
                loan.getLoanDate(),
                loan.getDueDate(),
                loan.getStatus().name()
        );
    }
}
