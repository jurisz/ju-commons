package org.juz.common.persistence.hibernate;

import com.google.common.collect.ImmutableMap;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.juz.common.persistence.EntityUniquenessRepository;
import org.juz.common.persistence.model.BaseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Map;

import static org.hibernate.criterion.Restrictions.eq;
import static org.hibernate.criterion.Restrictions.ne;

@Repository
class EntityUniquenessRepositoryBean implements EntityUniquenessRepository {

	private SessionFactory sessionFactory;

	@Autowired
	private EntityManager entityManager;

//	http://stackoverflow.com/questions/21329658/how-do-i-build-a-genericdao-using-querydsl	

	@Autowired
	public EntityUniquenessRepositoryBean(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public boolean checkUnique(Class<? extends BaseEntity> entityClass, String property, Object value) {
		Map<String, Object> propertyData = ImmutableMap.of(property, value);
		return checkUnique(entityClass, propertyData);
	}

	@Override
	public boolean checkUnique(Class<? extends BaseEntity> entityClass, String property, Object value, Long excludedEntityId) {
		Map<String, Object> propertyData = ImmutableMap.of(property, value);
		return checkUnique(entityClass, propertyData, excludedEntityId);
	}

	@Override
	public boolean checkUnique(Class<? extends BaseEntity> entityClass, Map<String, Object> propertyData) {
		Criteria criteria = criteria(entityClass);
		for (String property : propertyData.keySet()) {
			criteria.add(eq(property, propertyData.get(property)));
		}
		return criteria.list().isEmpty();
	}

	@Override
	public boolean checkUnique(Class<? extends BaseEntity> entityClass, Map<String, Object> propertyData, Long excludedEntityId) {
		Criteria criteria = criteria(entityClass);
		for (String property : propertyData.keySet()) {
			criteria.add(eq(property, propertyData.get(property)));
		}
		criteria.add(ne("id", excludedEntityId));
		return criteria.list().isEmpty();
	}

	private Criteria criteria(Class<? extends BaseEntity> entityClass) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(entityClass);
		criteria.setMaxResults(1);
		return criteria;
	}

}
