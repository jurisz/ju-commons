package org.juz.common.command;

import org.juz.common.api.ValidationError;

import java.util.Set;

public interface Validator<T extends Object> {

	public static final String ERROR_REQUIRED = "required";
	public static final String ERROR_TOO_SHORT = "too.short";
	public static final String ERROR_TOO_LONG = "too.long";
	public static final String ERROR_INCORRECT_FORMAT = "incorrect.format";
	public static final String ERROR_INCORRECT = "incorrect";
	public static final String ERROR_BAD_DATA = "bad.data";
	public static final String ERROR_UNSUPPORTED = "unsupported";

	Set<ValidationError> validate(T any);

}
