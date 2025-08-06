package com.francinjr.rentalbusiness.commons.core.domain.validation;

import java.lang.annotation.*;

@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueField {
    String message() default "Este valor já está em uso.";
    String fieldName();
    Class<?> entityClass();
}
