package org.juz.common.persistence;

import org.juz.common.persistence.model.BaseEntity;

public interface EntityUniqueCheckRepository {

	boolean checkIsUnique(Class<? extends BaseEntity> entityClass, String property, Object value);

	boolean checkIsUnique(Class<? extends BaseEntity> entityClass, String property, Object value, Long excludedEntityId);
}
