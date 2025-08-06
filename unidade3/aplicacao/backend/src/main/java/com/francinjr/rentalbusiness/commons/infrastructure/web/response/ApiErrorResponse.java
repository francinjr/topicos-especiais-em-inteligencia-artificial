package com.francinjr.rentalbusiness.commons.infrastructure.web.response;

import java.util.List;

import com.francinjr.rentalbusiness.commons.core.domain.validation.ValidationField;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApiErrorResponse<T> {
    private String message;
    private List<ValidationField> errors;
    private long timestamp;
    private String path;
}
