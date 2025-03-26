package com.mini.project.library_muhriz.controllers;


import com.mini.project.library_muhriz.dto.request.BookRequest;
import com.mini.project.library_muhriz.dto.response.BookResponse;
import com.mini.project.library_muhriz.dto.response.PageResponse;
import com.mini.project.library_muhriz.models.Book;
import com.mini.project.library_muhriz.services.BookService;
import com.mini.project.library_muhriz.utils.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/books")
@Validated
public class BookController {
    @Autowired
    private BookService bookService;


    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<BookResponse>>> getAllBooks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<BookResponse> booksPage = bookService.getAllBooks(PageRequest.of(page, size));
        return ResponseEntity.ok(ApiResponse.success("Daftar buku tersedia", new PageResponse<>(booksPage)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Optional<BookResponse>>> getBookById(@PathVariable UUID id) {
        return ResponseEntity.ok(ApiResponse.success("Detail buku", bookService.getBookById(id)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<BookResponse>> addBook(@Valid @RequestBody BookRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Buku berhasil ditambahkan", bookService.addBook(request)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<BookResponse>> updateBook(@PathVariable UUID id, @Valid @RequestBody BookRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Buku berhasil diperbarui", bookService.updateBook(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> softDeleteBook(@PathVariable UUID id) {
        return ResponseEntity.ok(ApiResponse.success(bookService.softDeleteBook(id)));
    }
}

