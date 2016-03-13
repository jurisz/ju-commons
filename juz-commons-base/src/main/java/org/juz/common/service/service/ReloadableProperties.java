package org.juz.common.service.service;

public interface ReloadableProperties {

	void reload();

	String getProperty(String name);

	String getProperty(String name, String defaultValue);
}
