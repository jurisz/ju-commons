package org.juz.common.persistence.hibernate;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.juz.common.persistence.EntityUniqueCheckRepository;
import org.juz.common.persistence.model.BaseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.hibernate.criterion.Restrictions.eq;
import static org.hibernate.criterion.Restrictions.ne;

@Repository
class EntityUniqueCheckRepositoryBean implements EntityUniqueCheckRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	public EntityUniqueCheckRepositoryBean(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public boolean checkIsUnique(Class<? extends BaseEntity> entityClass, String property, Object value) {
		Criteria criteria = criteria(entityClass);
		criteria.add(eq(property, value));
		return criteria.list().isEmpty();
	}

	@Override
	public boolean checkIsUnique(Class<? extends BaseEntity> entityClass, String property, Object value, Long excludedEntityId) {
		Criteria criteria = criteria(entityClass);
		criteria.add(eq(property, value));
		criteria.add(ne("id", excludedEntityId));
		return criteria.list().isEmpty();
	}

	private Criteria criteria(Class<? extends BaseEntity> entityClass) {
		Session session = entityManager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(entityClass);
		criteria.setMaxResults(1);
		return criteria;
	}
}
