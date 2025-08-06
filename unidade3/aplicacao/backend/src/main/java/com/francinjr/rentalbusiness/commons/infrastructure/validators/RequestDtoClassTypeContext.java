package com.francinjr.rentalbusiness.commons.infrastructure.validators;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Getter
@Setter
@RequestScope
@Component
public class RequestDtoClassTypeContext {
    private Class<?> dtoClass;
}
