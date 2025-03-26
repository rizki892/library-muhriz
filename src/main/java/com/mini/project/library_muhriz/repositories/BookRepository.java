package com.mini.project.library_muhriz.repositories;

import com.mini.project.library_muhriz.models.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, UUID> {

    @Query("SELECT b FROM Book b WHERE b.deletedAt IS NULL")
    Page<Book> findAllActiveBooks(Pageable pageable);

    @Query("SELECT b FROM Book b WHERE b.id = :id AND b.deletedAt IS NULL")
    Optional<Book> findByIdAndNotDeleted(@Param("id") UUID id);
}

