package com.mini.project.library_muhriz.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "books")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Book extends AuditModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String title;
    private String author;

    @Enumerated(EnumType.STRING)
    private BookStatus status = BookStatus.AVAILABLE;

    private LocalDateTime deletedAt; // ✅ Soft Delete

    public boolean isDeleted() {
        return deletedAt != null;
    }
}


