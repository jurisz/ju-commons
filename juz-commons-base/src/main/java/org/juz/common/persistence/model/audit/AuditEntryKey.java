package org.juz.common.persistence.model.audit;

public class AuditEntryKey {
	private Long entityId;
	private Long revision;

	public AuditEntryKey() {
	}

	public AuditEntryKey(Long entityId, Long revision) {
		this.entityId = entityId;
		this.revision = revision;
	}

	public Long getRevision() {
		return revision;
	}

	public Long getEntityId() {
		return entityId;
	}
}
