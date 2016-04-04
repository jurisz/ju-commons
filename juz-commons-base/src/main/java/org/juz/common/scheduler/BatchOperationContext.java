package org.juz.common.scheduler;

import org.juz.common.persistence.model.oplog.OperationLog;

import java.time.LocalDateTime;

public class BatchOperationContext {

	private LocalDateTime when;

	private OperationLog operationLog;

	public BatchOperationContext(LocalDateTime when, OperationLog operationLog) {
		this.when = when;
		this.operationLog = operationLog;
	}

	public LocalDateTime getWhen() {
		return when;
	}

	public OperationLog getOperationLog() {
		return operationLog;
	}
}
