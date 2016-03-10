package org.juz.common.api;

import com.google.common.base.Objects;

import javax.annotation.Nullable;

public class NonPrintable<T> {

	private T value;

	public NonPrintable(@Nullable T value) {
		this.value = value;
	}

	public static <T> NonPrintable<T> absent() {
		return new NonPrintable<T>(null);
	}

	public T getValue() {
		return value;
	}

	@Override
	public String toString() {
		return "*********";
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof NonPrintable) {
			@SuppressWarnings("unchecked")
			NonPrintable<T> that = (NonPrintable<T>) obj;
			return Objects.equal(this.value, that.value);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(value);
	}

}
