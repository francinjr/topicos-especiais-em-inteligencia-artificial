package com.francinjr.rentalbusiness.commons.core.domain.entities;

import java.util.List;

public class PaginationResult<T> {
    private final List<T> content;
    private final long totalElements;

    public PaginationResult(List<T> content, long totalElements) {
        this.content = content;
        this.totalElements = totalElements;
    }

    public List<T> getContent() {
        return content;
    }

    public long getTotalElements() {
        return totalElements;
    }
}
