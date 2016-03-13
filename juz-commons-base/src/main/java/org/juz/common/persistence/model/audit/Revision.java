package org.juz.common.persistence.model.audit;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;
import org.juz.common.Convertable;

import javax.persistence.*;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;

@Entity
@Table(name = "audit_revisions")
@RevisionEntity(AuditRevisionListener.class)
public class Revision implements Convertable<RevisionBean> {

	private static final int MAX_DETAILS_LENGTH = 500;

	@Id
	@GeneratedValue(generator = "audit_revisions_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "audit_revisions_seq", sequenceName = "audit_revisions_seq", allocationSize = 1)
	@Column(name = "id")
	@RevisionNumber
	private Long id;

	@Column(name = "revision_date")
	@RevisionTimestamp
	private Date revisionDate;

	@Column(name = "user_name", length = 100)
	private String user = "";

	@Column(name = "details", length = MAX_DETAILS_LENGTH)
	private String details = "";

	public Date getRevisionDate() {
		return revisionDate;
	}

	public Long getId() {
		return id;
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
		this.details = StringUtils.abbreviate(details, MAX_DETAILS_LENGTH);
	}

	@Override
	public RevisionBean toBean() {
		RevisionBean revision = new RevisionBean();
		revision.setId(getId());
		revision.setUser(getUser());
		revision.setDetails(getDetails());
		revision.setRevisionDate(Instant.ofEpochMilli(revisionDate.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime());
		return revision;
	}
}
