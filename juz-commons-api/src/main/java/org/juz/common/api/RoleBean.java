package org.juz.common.api;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RoleBean {

	private Long id;

	private String name;

	private String relatedLdapGroupName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRelatedLdapGroupName() {
		return relatedLdapGroupName;
	}

	public void setRelatedLdapGroupName(String relatedLdapGroupName) {
		this.relatedLdapGroupName = relatedLdapGroupName;
	}
}
