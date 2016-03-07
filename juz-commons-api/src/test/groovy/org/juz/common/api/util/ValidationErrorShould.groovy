package org.juz.common.api.util

import org.juz.common.api.ValidationError
import spock.lang.Specification

class ValidationErrorShould extends Specification {

	def "equals by name and property"() {
		given:
		ValidationError e = new ValidationError("email", "required")

		expect:
		e == new ValidationError("email", "required")
		e != new ValidationError("email", "bad.format")
		e != new ValidationError("mobile", "required")
	}

	def "printed string"() {
		given:
		ValidationError e = new ValidationError("email", "required")

		expect:
		e.toString() == "ValidationError{property=email, errorCode=required}"
	}
}
