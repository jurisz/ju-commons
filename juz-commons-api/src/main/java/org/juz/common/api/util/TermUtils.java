package org.juz.common.api.util;

import org.joda.time.LocalDate;
import org.juz.common.api.Term;
import org.juz.common.api.Term.TermUnit;


public class TermUtils {

	public static int DAYS_IN_MONTH = 30;

	/**
	 * build from "15 DAYS"
	 *
	 * @param valueAndUnit
	 * @return
	 */
	public static Term build(String valueAndUnit) {
		String[] data = valueAndUnit.split(" ");
		Integer value = Integer.valueOf(data[0]);
		String unit = data[1];
		if (unit.equals(TermUnit.MONTHS.name())) {
			return Term.months(value);
		} else if (unit.equals(TermUnit.DAYS.name())) {
			return Term.days(value);
		}
		return null;
	}

	public static boolean hasNoReminder(Term dividend, Term divisor) {
		return getDaysInTerm(dividend) % getDaysInTerm(divisor) == 0;
	}

	public static Term subtract(Term term1, Term term2) {
		if (term1.getUnit() == TermUnit.MONTHS && term2.getUnit() == TermUnit.MONTHS) {
			return new Term(term1.getValue() - term2.getValue(), TermUnit.MONTHS);
		}
		return new Term(getDaysInTerm(term1) - getDaysInTerm(term2), TermUnit.DAYS);
	}

	public static Term add(Term term1, Term term2) {
		if (term1.getUnit() == TermUnit.MONTHS && term2.getUnit() == TermUnit.MONTHS) {
			return new Term(term1.getValue() + term2.getValue(), TermUnit.MONTHS);
		}
		return new Term(getDaysInTerm(term1) + getDaysInTerm(term2), TermUnit.DAYS);
	}

	public static boolean isMonths(Term term) {
		return term.getUnit() == TermUnit.MONTHS;
	}

	public static boolean isDays(Term term) {
		return !isMonths(term);
	}

	public static boolean isG(Term left, Term right) {
		return getDaysInTerm(left) > getDaysInTerm(right);
	}

	public static boolean isL(Term left, Term right) {
		return getDaysInTerm(left) < getDaysInTerm(right);
	}

	public static final int getDaysInTerm(Term term) {
		if (isMonths(term)) {
			return DAYS_IN_MONTH * term.getValue();
		} else {
			return term.getValue();
		}
	}

	public static LocalDate addTermToDate(LocalDate date, Term term) {
		if (isMonths(term)) {
			return date.plusMonths(term.getValue());
		} else {
			return date.plusDays(term.getValue());
		}
	}

	public static LocalDate subtractTermFromDate(LocalDate date, Term term) {
		if (isMonths(term)) {
			return date.minusMonths(term.getValue());
		} else {
			return date.minusDays(term.getValue());
		}
	}
}
