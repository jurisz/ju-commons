package org.juz.common.persistence;

import org.juz.common.persistence.model.BaseEntity;

import java.io.Serializable;
import java.util.List;

public interface EntityRepository {

	<E extends BaseEntity, ID extends Serializable> ID persist(E entity);

	/**
	 * @return null if entity not found
	 */
	<E extends BaseEntity, ID extends Serializable> E getOptional(Class<E> entityClass, ID id);

	<E extends BaseEntity, ID extends Serializable> E getRequired(Class<E> entityClass, ID id);

	<E extends BaseEntity> void delete(E entity);

	<E extends BaseEntity> void lockForWrite(E entity);

	<E extends BaseEntity> void merge(E entity);

	<E extends BaseEntity> List<E> list(Class<E> entityClass);

}
