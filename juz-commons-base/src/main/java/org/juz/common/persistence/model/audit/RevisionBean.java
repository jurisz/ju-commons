package org.juz.common.persistence.model.audit;


import java.time.LocalDateTime;

public class RevisionBean {

	private Long id;

	private LocalDateTime revisionDate;

	private String user = "";

	private String details = "";

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getRevisionDate() {
		return revisionDate;
	}

	public void setRevisionDate(LocalDateTime revisionDate) {
		this.revisionDate = revisionDate;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

}
