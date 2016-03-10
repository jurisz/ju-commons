package org.juz.common.api.operationlog;

import java.time.LocalDateTime;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public class OperationLogBean {

	private Long id;

	private OperationLogType type;

	private LocalDateTime startDate;

	private LocalDateTime endDate;

	private LocalDateTime created;

	private String title;

	private String formattedDuration;

	private List<OperationLogPropertyBean> properties = newArrayList();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public OperationLogType getType() {
		return type;
	}

	public void setType(OperationLogType type) {
		this.type = type;
	}

	public LocalDateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}

	public LocalDateTime getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDateTime endDate) {
		this.endDate = endDate;
	}

	public LocalDateTime getCreated() {
		return created;
	}

	public void setCreated(LocalDateTime created) {
		this.created = created;
	}

	public List<OperationLogPropertyBean> getProperties() {
		return properties;
	}

	public void setProperties(List<OperationLogPropertyBean> properties) {
		this.properties = properties;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getFormattedDuration() {
		return formattedDuration;
	}

	public void setFormattedDuration(String formattedDuration) {
		this.formattedDuration = formattedDuration;
	}
}
