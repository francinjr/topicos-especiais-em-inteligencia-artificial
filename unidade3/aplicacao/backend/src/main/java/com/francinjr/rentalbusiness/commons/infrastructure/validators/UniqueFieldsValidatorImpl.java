package com.francinjr.rentalbusiness.commons.infrastructure.validators;

import com.francinjr.rentalbusiness.commons.core.domain.validation.UniqueField;
import com.francinjr.rentalbusiness.commons.core.domain.validation.ValidateUniqueFields;
import com.francinjr.rentalbusiness.commons.core.domain.validation.ValidationField;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Id;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.lang.reflect.Field;
import java.util.*;
import org.springframework.stereotype.Component;

@Component
public class UniqueFieldsValidatorImpl implements UniqueFieldsValidator {

    @PersistenceContext private EntityManager entityManager;
    private final RequestDtoClassTypeContext requestClassTypeContext;

    public UniqueFieldsValidatorImpl(RequestDtoClassTypeContext requestClassTypeContext) {
        this.requestClassTypeContext = requestClassTypeContext;
    }

    public List<ValidationField> validate(Object objectEntity) throws Exception {
        List<ValidationField> existingFieldValues = new ArrayList<>();

        Class<?> dtoClass = requestClassTypeContext.getDtoClass();
        String dtoFieldPath = "";

        return validate(dtoClass, dtoFieldPath, objectEntity, existingFieldValues);
    }

    private List<ValidationField> validate(
            Class<?> dtoClass,
            String dtoFieldPath,
            Object entityObject,
            List<ValidationField> existingFieldValues)
            throws Exception {

        if (dtoClass == null) {
            throw new Exception("A classe DTO está nula.");
        }

        boolean isUpdate = true;

        Field idField = entityObject.getClass().getDeclaredField("id");
        idField.setAccessible(true);

        Set<Field> dtoFieldsToValidate = getDtoFieldsToValidate(dtoClass);

        for (Field field : dtoFieldsToValidate) {
            field.setAccessible(true);

            if (field.isAnnotationPresent(UniqueField.class)) {
                String message = field.getAnnotation(UniqueField.class).message();
                Class<?> entityClass = field.getAnnotation(UniqueField.class).entityClass();
                String entityName = entityClass.getSimpleName();
                String entityFieldNameFromDto = field.getAnnotation(UniqueField.class).fieldName();

                for (Field entityField : entityObject.getClass().getDeclaredFields()) {
                    entityField.setAccessible(true);

                    Object entityId = getEntityId(entityObject);

                    if (entityId == null || (Long) entityId == 0) {
                        isUpdate = false;
                    }

                    if (entityField.getName().equals(entityFieldNameFromDto)
                            && entityObject.getClass().getSimpleName().equals(entityName)) {
                        try {
                            Object fieldValue = entityField.get(entityObject);
                            String jpql =
                                    String.format(
                                            "SELECT COUNT(e) FROM %s e WHERE e.%s = :value%s",
                                            entityName,
                                            entityFieldNameFromDto,
                                            isUpdate ? " AND e.id != :id" : "");

                            TypedQuery<Long> query = entityManager.createQuery(jpql, Long.class);
                            query.setParameter("value", fieldValue);
                            if (isUpdate) {
                                query.setParameter("id", entityId);
                            }

                            Long count = query.getSingleResult();
                            if (count != null && count > 0) {
                                String fullFieldPath;
                                if (dtoFieldPath.isEmpty()) {
                                    fullFieldPath = field.getName();
                                } else {
                                    fullFieldPath = dtoFieldPath + "." + field.getName();
                                }
                                existingFieldValues.add(
                                        new ValidationField(fullFieldPath, message));
                            }
                        } catch (Exception e) {
                            throw new RuntimeException(
                                    "Erro ao validar campo único via EntityManager", e);
                        }
                    }
                }

            } else if (field.isAnnotationPresent(ValidateUniqueFields.class)) {
                Class<?> nestedDtoClass = field.getType();
                String nestedFieldPath;
                if (dtoFieldPath.isEmpty()) {
                    nestedFieldPath = field.getName();
                } else {
                    nestedFieldPath = dtoFieldPath + "." + field.getName();
                }
                validate(nestedDtoClass, nestedFieldPath, entityObject, existingFieldValues);
            }
        }

        return existingFieldValues;
    }

    private Set<Field> getDtoFieldsToValidate(Class<?> dtoClass) {
        Set<Field> fieldsToValidate = new HashSet<>();
        for (Field field : dtoClass.getDeclaredFields()) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(UniqueField.class)
                    || field.isAnnotationPresent(ValidateUniqueFields.class)) {
                fieldsToValidate.add(field);
            }
        }
        return fieldsToValidate;
    }

    private Object getEntityId(Object entityObjectToValidate) throws Exception {
        for (Field field : entityObjectToValidate.getClass().getDeclaredFields()) {
            field.setAccessible(true);

            if (field.isAnnotationPresent(Id.class)) {
                return field.get(entityObjectToValidate);
            }
        }

        throw new Exception(
                "A entidade "
                        + entityObjectToValidate.getClass().getSimpleName()
                        + " não possui um campo anotado com @Id");
    }
}
