package com.hsbc.incident.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Page<T> {
    private List<T> content;
    private long total;
    private int pageNumber;
    private int pageSize;
    private int totalPages;

    public Page(List<T> content, long total, int pageNumber, int pageSize) {
        this.content = content;
        this.total = total;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.totalPages = (int) Math.ceil((double) total / pageSize);
    }
}

