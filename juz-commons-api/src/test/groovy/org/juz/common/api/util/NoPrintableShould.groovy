package org.juz.common.api.util

import org.juz.common.api.NonPrintable
import spock.lang.Specification

class NoPrintableShould extends Specification {

	def "print stars on toString"() {
		given:
		NonPrintable<String> nonPrintable = new NonPrintable<String>("password")

		expect:
		"*********" == nonPrintable.toString()
	}

	def "can read value"() {
		given:
		NonPrintable<String> nonPrintable = new NonPrintable<String>("password")

		expect:
		"password" == nonPrintable.value
	}

	def "work with null"() {
		given:
		NonPrintable<String> nonPrintable = new NonPrintable<String>(null)

		expect:
		null == nonPrintable.value
	}
}
