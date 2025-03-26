package com.mini.project.library_muhriz.services;



import com.mini.project.library_muhriz.dto.request.BookRequest;
import com.mini.project.library_muhriz.dto.response.BookResponse;
import com.mini.project.library_muhriz.models.Book;
import com.mini.project.library_muhriz.models.BookStatus;
import com.mini.project.library_muhriz.repositories.BookRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    public Page<BookResponse> getAllBooks(Pageable pageable) {
        return bookRepository.findAllActiveBooks(pageable)
                .map(book -> new BookResponse(
                        book.getId(),
                        book.getTitle(),
                        book.getAuthor(),
                        book.getStatus().name(),
                        book.isDeleted()
                ));
    }

    public Optional<BookResponse> getBookById(UUID id) {
        return bookRepository.findByIdAndNotDeleted(id)
                .map(book -> new BookResponse(book.getId(),book.getTitle(), book.getAuthor(),book.getStatus().name(),book.isDeleted()));
    }

    public BookResponse addBook(BookRequest request) {
        Book book = new Book();
        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setStatus(BookStatus.AVAILABLE);
        bookRepository.save(book);
        return new BookResponse(book.getId(),book.getTitle(), book.getAuthor(),book.getStatus().name(),book.isDeleted());
    }

    public BookResponse updateBook(UUID id, BookRequest request) {
        Book book = bookRepository.findByIdAndNotDeleted(id)
                .orElseThrow(() -> new RuntimeException("Buku tidak ditemukan"));

        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        bookRepository.save(book);
        return new BookResponse(book.getId(),book.getTitle(), book.getAuthor(),book.getStatus().name(),book.isDeleted());
    }

    public String softDeleteBook(UUID id) {
        Book book = bookRepository.findByIdAndNotDeleted(id)
                .orElseThrow(() -> new RuntimeException("Buku tidak ditemukan"));

        book.setDeletedAt(LocalDateTime.now());
        bookRepository.save(book);

        return "Buku berhasil dihapus (soft delete)";
    }
}

