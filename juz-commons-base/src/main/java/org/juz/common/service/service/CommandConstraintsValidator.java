package org.juz.common.service.service;

import org.juz.common.api.Command;
import org.juz.common.api.ValidationError;
import org.juz.common.command.CommandValidator;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;

public class CommandConstraintsValidator<C extends Command> implements CommandValidator<C> {

	private final static javax.validation.Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

	public Set<ValidationError> validate(C command) {
		Set<ConstraintViolation<C>> constraintViolations = validator.validate(command);
		Set<ValidationError> validationErrors = newHashSet();

		constraintViolations.forEach(constraintViolation -> {
			Class constraintType = constraintViolation.getConstraintDescriptor().getAnnotation().annotationType();

			String validationError;
			if (constraintType == NotNull.class) {
				validationError = ERROR_REQUIRED;
			} else if (constraintType == Pattern.class) {
				validationError = ERROR_INCORRECT_FORMAT;
			} else {
				validationError = ERROR_INCORRECT;
			}

			validationErrors.add(new ValidationError(constraintViolation.getPropertyPath().toString(), validationError));
		});

		return validationErrors;
	}
}
