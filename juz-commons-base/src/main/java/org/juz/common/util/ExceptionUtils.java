package org.juz.common.util;

import java.util.concurrent.Callable;

public class ExceptionUtils {

	public static <T> T propagateCatchableException(Callable<T> callable) {
		try {
			return callable.call();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public static void propagateCatchableRunException(Runnable runnable) {
		try {
			runnable.run();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
}
