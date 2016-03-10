package org.juz.common.command

import com.google.common.collect.ImmutableSet
import org.juz.common.api.Command
import org.juz.common.api.ValidationError
import org.juz.common.api.ValidationResult
import spock.lang.Specification

class ValidationExceptionShould extends Specification {

	def "exception message should be readable"() {
		given:
		ValidationResult validationResult = new ValidationResult();
		ValidationError error = new ValidationError("email", Validator.ERROR_INCORRECT_FORMAT, "bla@@site.lv");
		validationResult.addErrors(ImmutableSet.of(error));

		when:
		ValidationException ex = new ValidationException(new MyCommand(), validationResult);

		then:
		ex.getMessage() == "Command MyCommand validation failed: [ValidationError{property=email, errorCode=incorrect.format, wrongValue=bla@@site.lv}]";
	}

	private class MyCommand implements Command {

	}
}
