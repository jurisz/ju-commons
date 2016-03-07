package org.juz.common.api;

public class PropertyValueBean {

	private String name;
	private Object value;

	public PropertyValueBean() {
	}

	public PropertyValueBean(String name, Object value) {
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public Object getValue() {
		return value;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "" + value;
	}
}
