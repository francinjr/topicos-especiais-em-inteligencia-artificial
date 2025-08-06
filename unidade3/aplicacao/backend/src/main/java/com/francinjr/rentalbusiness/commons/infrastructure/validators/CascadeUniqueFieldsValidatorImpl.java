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
public class CascadeUniqueFieldsValidatorImpl implements UniqueFieldsValidator {

  @PersistenceContext private EntityManager entityManager;
  private final RequestDtoClassTypeContext requestClassTypeContext;

  public CascadeUniqueFieldsValidatorImpl(RequestDtoClassTypeContext requestClassTypeContext) {
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

    Set<Object> allEntityObjectsToValidate = new HashSet<>();
    allEntityObjectsToValidate.add(entityObject);
    allEntityObjectsToValidate.addAll(
        getEntityFieldsAndValuesToValidate(entityObject, new HashSet<>()));

    for (Field field : dtoFieldsToValidate) {
      field.setAccessible(true);

      if (field.isAnnotationPresent(UniqueField.class)) {
        String message = field.getAnnotation(UniqueField.class).message();
        Class<?> entityClass = field.getAnnotation(UniqueField.class).entityClass();
        String entityName = entityClass.getSimpleName();
        String entityFieldNameFromDto = field.getAnnotation(UniqueField.class).fieldName();

        for (Object entityObjectToValidate : allEntityObjectsToValidate) {
          for (Field entityField : entityObjectToValidate.getClass().getDeclaredFields()) {
            entityField.setAccessible(true);

            Object entityId = getEntityId(entityObjectToValidate);

            if (entityId == null || (Long) entityId == 0) {
              isUpdate = false;
            }

            if (entityField.getName().equals(entityFieldNameFromDto)
                && entityObjectToValidate.getClass().getSimpleName().equals(entityName)) {
              try {
                Object fieldValue = entityField.get(entityObjectToValidate);
                String jpql =
                    String.format(
                        "SELECT COUNT(e) FROM %s e WHERE e.%s = :value%s",
                        entityName, entityFieldNameFromDto, isUpdate ? " AND e.id != :id" : "");

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
                  existingFieldValues.add(new ValidationField(fullFieldPath, message));
                }
              } catch (Exception e) {
                throw new RuntimeException("Erro ao validar campo único via EntityManager", e);
              }
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

  private Set<Object> getEntityFieldsAndValuesToValidate(Object entityObject, Set<Object> objects)
      throws IllegalAccessException {
    if (entityObject == null) return objects;

    for (Field field : entityObject.getClass().getDeclaredFields()) {
      field.setAccessible(true);
      if (field.isAnnotationPresent(ValidateUniqueFields.class)) {
        Object nestedObject = field.get(entityObject);
        if (nestedObject != null && !objects.contains(nestedObject)) {
          objects.add(nestedObject);
          getEntityFieldsAndValuesToValidate(nestedObject, objects);
        }
      }
    }
    return objects;
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
