package com.mini.project.library_muhriz.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class LoanRequest {
    @NotNull(message = "ID Buku tidak boleh kosong")
    private UUID bookId;
}

