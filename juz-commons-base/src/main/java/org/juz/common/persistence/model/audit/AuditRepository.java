package org.juz.common.persistence.model.audit;


import org.juz.common.persistence.model.BaseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

@Transactional(readOnly = true)
public interface AuditRepository {

	<E extends BaseEntity, ID extends Serializable> E getRequiredForRevision(Class<E> entityClass, ID id, Number revisionNumber);

	List<Revision> listRevisions(Class<?> entityClass, Serializable primaryKey);

	AuditEntryKey getLastRevisionAuditEntryKey(Class<?> entityClass, Long entityId);

	<E extends BaseEntity, ID extends Serializable> E getRequiredForRevision(Class<E> entityClass, AuditEntryKey auditEntryKey);
}
