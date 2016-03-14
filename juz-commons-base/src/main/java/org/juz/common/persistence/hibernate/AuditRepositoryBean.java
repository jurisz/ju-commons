package org.juz.common.persistence.hibernate;

import com.google.common.base.Preconditions;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.juz.common.persistence.model.BaseEntity;
import org.juz.common.persistence.model.audit.AuditEntryKey;
import org.juz.common.persistence.model.audit.AuditRepository;
import org.juz.common.persistence.model.audit.Revision;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

@Repository
class AuditRepositoryBean implements AuditRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public <E extends BaseEntity, ID extends Serializable> E getRequiredForRevision(Class<E> entityClass, ID id, Number revisionNumber) {
		AuditReader reader = AuditReaderFactory.get(entityManager);
		E entity = reader.find(entityClass, id, revisionNumber);
		Preconditions.checkNotNull(entity, "Entity %s  not found by id %s and revision %s", entityClass.getName(), id, revisionNumber);
		return entity;
	}

	@Override
	public List<Revision> listRevisions(Class<?> entityClass, Serializable primaryKey) {
		AuditReader reader = AuditReaderFactory.get(entityManager);
		List<Number> revisionNumbers = reader.getRevisions(entityClass, primaryKey);

		List<Revision> revisions = newArrayList();
		for (Number revisionNumber : revisionNumbers) {
			Revision revision = entityManager.find(Revision.class, revisionNumber);
			revisions.add(revision);
		}
		return revisions;
	}

	@Override
	public <E extends BaseEntity, ID extends Serializable> E getRequiredForRevision(Class<E> entityClass, AuditEntryKey auditEntryKey) {
		return getRequiredForRevision(entityClass, auditEntryKey.getEntityId(), auditEntryKey.getRevision());
	}

	@Override
	public AuditEntryKey getLastRevisionAuditEntryKey(Class<?> entityClass, Long entityId) {
		AuditReader reader = AuditReaderFactory.get(entityManager);
		List<Number> revisionNumbers = reader.getRevisions(entityClass, entityId);

		return new AuditEntryKey(entityId, revisionNumbers.get(revisionNumbers.size() - 1).longValue());
	}
}
