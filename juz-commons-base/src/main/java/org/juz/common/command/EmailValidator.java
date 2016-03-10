package org.juz.common.command;

import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
import org.juz.common.api.ValidationError;

import java.util.Set;

public class EmailValidator implements Validator<String> {

	public static final String MAIL_REGEX = "^[\\w\\-]([\\.\\w\\-])*@([\\w\\-]+\\.)+[A-Za-z]{2,6}$";

	@Override
	public Set<ValidationError> validate(String email) {
		Set<ValidationError> errors = Sets.newHashSet();
		email = StringUtils.trimToEmpty(email);
		if (!email.matches(MAIL_REGEX)) {
			errors.add(new ValidationError("email", Validator.ERROR_INCORRECT_FORMAT));
		}
		return errors;
	}
}
