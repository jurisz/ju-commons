package org.juz.common.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class GenericTypeUtil {

	/**
	 * for MyHandler implements Handler<A,B> will return A
	 *
	 * @param clazz
	 * @param <T>
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static final <T> Class<T> extractInterfaceFirstTypeArgument(Class<?> clazz) {
		if (clazz.equals(Object.class)) {
			throw new IllegalArgumentException("Reached Object, no interfaces found");
		}
		Type[] interfaces = clazz.getGenericInterfaces();
		ParameterizedType type = null;
		if (interfaces.length == 0) {
			Type genericSuperclass = clazz.getGenericSuperclass();
			return extractInterfaceFirstTypeArgument((Class<?>) genericSuperclass);
		} else if (interfaces.length > 0 && interfaces[0] instanceof ParameterizedType) {
			type = (ParameterizedType) interfaces[0];
			return (Class<T>) type.getActualTypeArguments()[0];
		}
		throw new IllegalArgumentException("Class " + clazz + " generic interface first argument can't be extracted!");
	}

}
