package org.juz.common.command

import org.juz.common.api.ValidationError
import spock.lang.Specification

class EmailValidatorShould extends Specification {

	EmailValidator validator;

	void setup() {
		validator = new EmailValidator();
	}

	def "with invalid format"() {
		expect:
		validator.validate("aaa@test@gmail.com").contains(new ValidationError("email", "incorrect.format"))
	}

	def "valid format"() {
		expect:
		validator.validate("test@gmail.com").isEmpty()
		validator.validate("juris.zubkans@inbox.lv").isEmpty()
	}

	def "null is not valid"() {
		expect:
		false == validator.validate(null).isEmpty()
	}

	def "empty is not valid"() {
		expect:
		false == validator.validate("").isEmpty()
		false == validator.validate(" ").isEmpty()
	}

	def "bad emails"() {
		expect:
		false == validator.validate("test@gmail.com.").isEmpty()
		false == validator.validate("rupeklis129@.tvnet.lv").isEmpty()
		false == validator.validate("einis531@delfi.lv`").isEmpty()
		false == validator.validate("särkkä.lindström@gmail.com").isEmpty()
	}

	def "with dash is valid"() {
		expect:
		validator.validate("uldis.petrovs-kalns@inbox.lv").isEmpty()
		validator.validate("uldis.petrovs-@inbox.lv").isEmpty()
	}

	def "invalid domain name"() {
		expect:
		false == validator.validate("uldis.petrovs-@inbox").isEmpty()
	}

	def "digits are valid"() {
		expect:
		validator.validate("12@345.lv").isEmpty()
	}

	def "one symbol emails are valid"() {
		expect:
		validator.validate("-@345.lv").isEmpty()
		validator.validate("d@345.lv").isEmpty()
	}
}
