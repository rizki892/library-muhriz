package com.mini.project.library_muhriz.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class BookResponse {
    private UUID id;
    private String title;
    private String author;
    private String status;
    private boolean deleted;

    public BookResponse(UUID id,String title, String author, String status, boolean deleted) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.status = status;
        this.deleted = deleted;
    }
}

