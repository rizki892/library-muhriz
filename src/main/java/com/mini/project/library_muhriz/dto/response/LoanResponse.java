package com.mini.project.library_muhriz.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class LoanResponse {
    private UUID id;
    private String user;
    private String book;
    private LocalDate loanDate;
    private LocalDate dueDate;
    private String status;
    public LoanResponse(UUID id,String user, String book, LocalDate loanDate, LocalDate dueDate,String status) {
        this.id = id;
        this.user = user;
        this.book = book;
        this.status = status;
        this.loanDate = loanDate;
        this.dueDate = dueDate;
    }
}
