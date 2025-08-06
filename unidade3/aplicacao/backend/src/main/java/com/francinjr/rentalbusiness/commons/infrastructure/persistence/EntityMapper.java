package com.francinjr.rentalbusiness.commons.infrastructure.persistence;

import com.francinjr.rentalbusiness.commons.core.domain.entities.DomainModel;

public interface EntityMapper<M extends DomainModel, E extends EntityModel> {
    E modelToEntity(M model);
    M entityToModel(E entity);
}
