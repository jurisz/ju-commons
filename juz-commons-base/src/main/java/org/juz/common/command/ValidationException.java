package org.juz.common.command;


import org.juz.common.api.Command;
import org.juz.common.api.ValidationError;
import org.juz.common.api.ValidationResult;

public class ValidationException extends RuntimeException {

	private final ValidationResult validationResult;

	public ValidationException(Command command, ValidationResult validationResult) {
		super("Command " + command.getClass().getSimpleName() + " validation failed: " + validationResult.getErrors());
		this.validationResult = validationResult;
	}

	public ValidationException(Command command, ValidationError validationError) {
		super("Command " + command.getClass().getSimpleName() + " validation failed: " + validationError);
		validationResult = new ValidationResult(validationError);
	}

	public ValidationException(String message, ValidationResult validationResult) {
		super(message);
		this.validationResult = validationResult;
	}

	public ValidationException(String message, ValidationError validationError) {
		super(message);
		this.validationResult = new ValidationResult(validationError);
	}

	public ValidationResult getValidationResult() {
		return validationResult;
	}

}
