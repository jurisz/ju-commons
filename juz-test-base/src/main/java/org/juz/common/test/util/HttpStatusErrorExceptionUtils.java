package org.juz.common.test.util;

import com.google.gson.Gson;
import org.juz.common.api.ValidationError;
import org.juz.common.api.ValidationResult;
import org.springframework.web.client.HttpStatusCodeException;

import java.util.Set;

public class HttpStatusErrorExceptionUtils {

	public static String requireErrorWithMessage(Runnable runnable) {
		try {
			runnable.run();
		} catch (HttpStatusCodeException ex) {
			return ex.getResponseBodyAsString();
		}
		throw new AssertionError("Expected HTTP error is not thrown");
	}

	public static Set<ValidationError> requireErrorWithValidationErrors(Runnable runnable) {
		try {
			runnable.run();
		} catch (HttpStatusCodeException ex) {
			ValidationResult validationResult = new Gson().fromJson(ex.getResponseBodyAsString(), ValidationResult.class);
			return validationResult.getErrors();
		}
		throw new AssertionError("Expected HTTP error is not thrown");
	}

}
