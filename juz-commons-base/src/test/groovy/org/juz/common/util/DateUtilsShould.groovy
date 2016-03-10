package org.juz.common.util

import spock.lang.Specification

import static org.juz.common.util.DateTimeUtils.date
import static org.juz.common.util.DateTimeUtils.dateTime

class DateUtilsShould extends Specification {

	void cleanup() {
		DateTimeUtils.setDeltaHours(0)
	}

	def "isLe"() {
		expect:
		DateTimeUtils.isLe(date("2010-01-01"), date("2010-01-01"))
		DateTimeUtils.isLe(date("2010-01-01"), date("2010-01-02"))
		!DateTimeUtils.isLe(date("2010-01-02"), date("2010-01-01"))

		DateTimeUtils.isLe(dateTime("2010-01-01T01:15:00"), dateTime("2010-01-01T01:15:00"))
		DateTimeUtils.isLe(date("2010-01-01").atStartOfDay(), date("2010-01-02").atStartOfDay())
		!DateTimeUtils.isLe(date("2010-01-02").atStartOfDay(), date("2010-01-01").atStartOfDay())
	}

	def "isL"() {
		expect:
		!DateTimeUtils.isL(date("2010-01-01"), date("2010-01-01"))
		DateTimeUtils.isL(date("2010-01-01"), date("2010-01-02"))
		!DateTimeUtils.isL(date("2010-01-02"), date("2010-01-01"))

		!DateTimeUtils.isL(dateTime("2010-01-01T01:15:00"), dateTime("2010-01-01T01:15:00"))
		DateTimeUtils.isL(date("2010-01-01").atStartOfDay(), date("2010-01-02").atStartOfDay())
		!DateTimeUtils.isL(date("2010-01-02").atStartOfDay(), date("2010-01-01").atStartOfDay())
	}

	def "isGe()"() {
		expect:
		DateTimeUtils.isGe(date("2010-01-02"), date("2010-01-01"))
		DateTimeUtils.isGe(date("2010-01-02"), date("2010-01-02"))
		!DateTimeUtils.isGe(date("2010-01-01"), date("2010-01-02"))

		DateTimeUtils.isGe(dateTime("2010-01-01T01:15:00"), dateTime("2010-01-01T01:15:00"))
		DateTimeUtils.isGe(date("2010-01-02").atStartOfDay(), date("2010-01-02").atStartOfDay())
		!DateTimeUtils.isGe(date("2010-01-01").atStartOfDay(), date("2010-01-02").atStartOfDay())
	}

	def "isG"() {
		expect:
		DateTimeUtils.isG(date("2010-01-02"), date("2010-01-01"))
		!DateTimeUtils.isG(date("2010-01-02"), date("2010-01-02"))
		!DateTimeUtils.isG(date("2010-01-01"), date("2010-01-02"))

		DateTimeUtils.isG(dateTime("2010-01-02T01:15:00"), dateTime("2010-01-01T01:15:00"))
		!DateTimeUtils.isG(date("2010-01-02").atStartOfDay(), date("2010-01-02").atStartOfDay())
		!DateTimeUtils.isG(date("2010-01-01").atStartOfDay(), date("2010-01-02").atStartOfDay())
	}

}
