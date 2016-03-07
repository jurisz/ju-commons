package org.juz.common.api;

public class AuditEntryKey {
	private Long entityId;
	private Long revision;

	@Deprecated
	AuditEntryKey() {
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
