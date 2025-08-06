package com.francinjr.rentalbusiness.commons.infrastructure.web.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApiSuccessResponse<T> {
    private String message;
    private T data;
    private long timestamp;
    private String path;
}
