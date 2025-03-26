package com.mini.project.library_muhriz.dto.response;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
public class PageResponse<T> {
    private int page;
    private int totalPages;
    private long totalCount;
    private List<T> data;

    public PageResponse(Page<T> pageData) {
        this.page = pageData.getNumber() + 1; // Page index dimulai dari 0, jadi kita tambahkan 1
        this.totalPages = pageData.getTotalPages();
        this.totalCount = pageData.getTotalElements();
        this.data = pageData.getContent();
    }
}

