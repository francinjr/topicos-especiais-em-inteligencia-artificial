package com.francinjr.rentalbusiness.commons.infrastructure.web.response;

import com.francinjr.rentalbusiness.commons.core.domain.validation.ValidationField;

import java.util.Arrays;
import java.util.List;

public class ResponseUtil {

    public static <T> ApiSuccessResponse<T> success(T data, String message, String path) {
        ApiSuccessResponse<T> response = new ApiSuccessResponse<>();
        response.setMessage(message);
        response.setData(data);
        response.setTimestamp(System.currentTimeMillis());
        response.setPath(path);
        return response;
    }

    public static <T> ApiErrorResponse<T> error(List<ValidationField> errors, String message, String path) {
        ApiErrorResponse<T> response = new ApiErrorResponse<>();
        response.setMessage(message);
        response.setErrors(errors);
        response.setTimestamp(System.currentTimeMillis());
        response.setPath(path);
        return response;
    }

    public static <T> ApiErrorResponse<T> error(ValidationField error, String message, String path) {
        return error(Arrays.asList(error), message, path);
    }
}
