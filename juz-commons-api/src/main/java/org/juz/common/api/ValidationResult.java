package org.juz.common.api;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

import java.util.Set;

public class ValidationResult {

	private Set<ValidationError> errors = Sets.newHashSet();

	public ValidationResult() {
	}

	public ValidationResult(ValidationError error) {
		this.errors.add(error);
	}

	public static ValidationResult noErrors() {
		return new ValidationResult();
	}

	public static ValidationResult withSingleError(ValidationError error) {
		ValidationResult result = new ValidationResult();
		result.addError(error);
		return result;
	}

	public static ValidationResult withErrors(Set<ValidationError> errors) {
		ValidationResult result = new ValidationResult();
		result.addErrors(errors);
		return result;
	}

	public Set<ValidationError> getErrors() {
		return ImmutableSet.copyOf(errors);
	}

	public boolean hasErrors() {
		return !errors.isEmpty();
	}

	public void addErrors(Set<ValidationError> errors) {
		this.errors.addAll(errors);
	}

	public void addError(ValidationError error) {
		this.errors.add(error);
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("errors", errors)
				.toString();
	}
}
