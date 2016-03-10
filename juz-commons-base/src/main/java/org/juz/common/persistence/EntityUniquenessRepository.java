package org.juz.common.persistence;

import org.juz.common.persistence.model.BaseEntity;

import java.util.Map;

public interface EntityUniquenessRepository {

	boolean checkUnique(Class<? extends BaseEntity> entityClass, String property, Object value);

	boolean checkUnique(Class<? extends BaseEntity> entityClass, String property, Object value, Long excludedEntityId);

	boolean checkUnique(Class<? extends BaseEntity> entityClass, Map<String, Object> propertyData);

	boolean checkUnique(Class<? extends BaseEntity> entityClass, Map<String, Object> propertyData, Long excludedEntityId);

}
