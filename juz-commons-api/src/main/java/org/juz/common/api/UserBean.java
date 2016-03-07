package org.juz.common.api;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UserBean {

	private Long id;

	private String userName;

	private UserAuthenticatedByType authenticatedBy;

	private UserRolesProvidedByType rolesProvidedBy;

	private String dbManagedRoleName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public UserAuthenticatedByType getAuthenticatedBy() {
		return authenticatedBy;
	}

	public void setAuthenticatedBy(UserAuthenticatedByType authenticatedBy) {
		this.authenticatedBy = authenticatedBy;
	}

	public UserRolesProvidedByType getRolesProvidedBy() {
		return rolesProvidedBy;
	}

	public void setRolesProvidedBy(UserRolesProvidedByType rolesProvidedBy) {
		this.rolesProvidedBy = rolesProvidedBy;
	}

	public String getDbManagedRoleName() {
		return dbManagedRoleName;
	}

	public void setDbManagedRoleName(String dbManagedRoleName) {
		this.dbManagedRoleName = dbManagedRoleName;
	}
}
