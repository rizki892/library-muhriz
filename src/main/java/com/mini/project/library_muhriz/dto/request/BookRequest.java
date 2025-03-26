package com.mini.project.library_muhriz.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookRequest {
    @NotBlank(message = "Judul buku tidak boleh kosong")
    private String title;

    @NotBlank(message = "Nama penulis tidak boleh kosong")
    private String author;
}
