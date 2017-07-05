package org.juz.common.api;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

public class ValidationError {

	private String property;

	private String errorCode;

	private Object wrongValue;

	public ValidationError() {
	}

	public ValidationError(String property, String errorCode) {
		this.property = property;
		this.errorCode = errorCode;
	}

	public ValidationError(String property, String errorCode, Object wrongValue) {
		this(property, errorCode);
		this.wrongValue = wrongValue;
	}

	public String getProperty() {
		return property;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public Object getWrongValue() {
		return wrongValue;
	}

	@Override
	public final int hashCode() {
		return Objects.hashCode(property, errorCode);
	}

	@Override
	public final boolean equals(Object obj) {
		if (obj instanceof ValidationError) {
			ValidationError other = (ValidationError) obj;
			return Objects.equal(this.property, other.property) && Objects.equal(this.errorCode, other.errorCode);
		} else {
			return false;
		}
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).omitNullValues()
				.add("property", property)
				.add("errorCode", errorCode)
				.add("wrongValue", wrongValue)
				.toString();
	}
}
