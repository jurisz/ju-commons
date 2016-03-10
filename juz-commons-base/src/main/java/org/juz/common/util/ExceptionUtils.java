package org.juz.common.util;

import java.util.concurrent.Callable;

import static com.google.common.base.Throwables.propagate;

public class ExceptionUtils {

	public static <T> T propagateCatchableException(Callable<T> callable) {
		try {
			return callable.call();
		} catch (Exception ex) {
			throw propagate(ex);
		}
	}

	public static void propagateCatchableRunException(Runnable runnable) {
		try {
			runnable.run();
		} catch (Exception ex) {
			propagate(ex);
		}
	}
}
