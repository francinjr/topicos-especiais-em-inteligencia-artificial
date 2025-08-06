package com.francinjr.rentalbusiness.components.users;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.francinjr.rentalbusiness.commons.core.domain.validation.ValidationField;
import com.francinjr.rentalbusiness.commons.infrastructure.web.response.ApiErrorResponse;
import com.francinjr.rentalbusiness.commons.infrastructure.web.response.ResponseUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void handle(HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException accessDeniedException) throws IOException {

        ApiErrorResponse<Void> apiError = ResponseUtil.error(
                new ValidationField("Erro", "Sem autorização"),
                "Você não tem autorização para realizar essa operação.",
                request.getRequestURI()
        );

        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(apiError));
    }
}
