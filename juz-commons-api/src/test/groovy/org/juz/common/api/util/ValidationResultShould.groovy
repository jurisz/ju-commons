package org.juz.common.api.util

import com.google.common.collect.ImmutableSet
import org.juz.common.api.ValidationError
import org.juz.common.api.ValidationResult
import spock.lang.Specification

class ValidationResultShould extends Specification {

	def "error checking demo"() {
		given:
		ValidationResult validationResult = new ValidationResult();
		ValidationError error = new ValidationError("email", "required");
		validationResult.addErrors(ImmutableSet.of(error));

		expect:
		validationResult.getErrors().contains(error)
		validationResult.getErrors().contains(new ValidationError("email", "required"))
		!validationResult.getErrors().contains(new ValidationError("email", "bla.bla"))

		validationResult.toString() == "ValidationResult{errors=[ValidationError{property=email, errorCode=required}]}"
	}

	def "empty errors hasError should"() {
		given:
		ValidationResult validationResult = new ValidationResult()

		expect:
		!validationResult.hasErrors()
	}

	def "on error hasError should be true"() {
		given:
		ValidationResult validationResult = new ValidationResult()
		validationResult.addErrors(ImmutableSet.of(new ValidationError("email", "required")))

		expect:
		validationResult.hasErrors()
	}

}
