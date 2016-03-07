package org.juz.common.api;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Term {

	public enum TermUnit {
		MONTHS, DAYS
	}

	private Integer value;
	private TermUnit unit;

	public static Term months(int months) {
		return new Term(months, TermUnit.MONTHS);
	}

	public static Term days(int days) {
		return new Term(days, TermUnit.DAYS);
	}

	public Term() {
	}

	public Term(Integer value, TermUnit unit) {
		super();
		this.value = value;
		this.unit = unit;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public TermUnit getUnit() {
		return unit;
	}

	public void setUnit(TermUnit unit) {
		this.unit = unit;
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}

	@Override
	public String toString() {
		return value + " " + unit;
	}

}
