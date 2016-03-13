package org.juz.common.service.service;

public enum JMSPriority {
	LOW(1), NORMAL(4), HIGH(8);

	private final int jmsPriority;

	JMSPriority(int jmsPriority) {
		this.jmsPriority = jmsPriority;
	}

	public int getJmsPriority() {
		return this.jmsPriority;
	}
}