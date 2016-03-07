package org.juz.common.api.util

import org.joda.time.LocalDate
import org.juz.common.api.Term
import spock.lang.Specification

class TermUtilsShould extends Specification {

	def "Term build from string"() {
		expect:
		Term.days(15) == TermUtils.build("15 DAYS");
		Term.months(12) == TermUtils.build("12 MONTHS");

		null == TermUtils.build("12 day")
		null == TermUtils.build("12 days")
	}

	def "compare"() {
		expect:
		TermUtils.isL(Term.days(29), Term.months(1))
		TermUtils.isG(Term.days(31), Term.months(1))
	}

	def "add term to date"() {
		given:
		LocalDate date1 = new LocalDate(2014, 1, 10)
		LocalDate date2 = new LocalDate(2014, 1, 31)

		expect:
		new LocalDate(2014, 3, 10) == TermUtils.addTermToDate(date1, Term.months(2))
		new LocalDate(2014, 2, 9) == TermUtils.addTermToDate(date1, Term.days(30))
		new LocalDate(2014, 2, 28) == TermUtils.addTermToDate(date2, Term.months(1))
		new LocalDate(2014, 3, 31) == TermUtils.addTermToDate(date2, Term.months(2))
	}

	def "subtract term from date"() {
		given:
		LocalDate date1 = new LocalDate(2014, 1, 10)
		LocalDate date2 = new LocalDate(2014, 1, 31)

		expect:
		new LocalDate(2013, 11, 10) == TermUtils.subtractTermFromDate(date1, Term.months(2))
		new LocalDate(2013, 12, 11) == TermUtils.subtractTermFromDate(date1, Term.days(30))
		new LocalDate(2013, 12, 31) == TermUtils.subtractTermFromDate(date2, Term.months(1))
		new LocalDate(2013, 11, 30) == TermUtils.subtractTermFromDate(date2, Term.months(2))

		new LocalDate(2014, 1, 31) == TermUtils.subtractTermFromDate(new LocalDate(2014, 3, 2), Term.days(30))
	}
}
