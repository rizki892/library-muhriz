package com.mini.project.library_muhriz.controllers;

import com.mini.project.library_muhriz.dto.request.LoanRequest;
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
    public ResponseEntity<ApiResponse<List<Loan>>> getOverdueLoans() {
        return ResponseEntity.ok(ApiResponse.success("Daftar buku yang telat dikembalikan", loanService.getOverdueLoans()));
    }

    @GetMapping("/active")
    public ResponseEntity<ApiResponse<List<Loan>>> getActiveLoans() {
        return ResponseEntity.ok(ApiResponse.success("Daftar buku yang sedang dipinjam", loanService.getActiveLoans()));
    }
}
