package org.juz.common.api;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UserLoginBean {

	private String name;
	private String password;

	public UserLoginBean() {
	}

	public UserLoginBean(String name, String password) {
		this.name = name;
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
