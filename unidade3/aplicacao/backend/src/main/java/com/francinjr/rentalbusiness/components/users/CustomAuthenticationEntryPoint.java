package com.francinjr.rentalbusiness.components.users;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.francinjr.rentalbusiness.commons.core.domain.validation.ValidationField;
import com.francinjr.rentalbusiness.commons.infrastructure.web.response.ApiErrorResponse;
import com.francinjr.rentalbusiness.commons.infrastructure.web.response.ResponseUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException) throws IOException {

        ApiErrorResponse<Void> apiError = ResponseUtil.error(
                new ValidationField("Token", "Token expirado, inválido ou ausente"),
                "Você precisa estar autenticado para executar essa operação",
                request.getRequestURI()
        );

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(apiError));
    }
}