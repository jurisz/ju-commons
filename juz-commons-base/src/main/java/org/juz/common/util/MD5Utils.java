package org.juz.common.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

public final class MD5Utils {

	private MD5Utils() {
	}

	public static String md5(String string) {
		return md5(string, null, false);
	}

	public static String uppercaseMd5(String string) {
		return md5(string, null, true);
	}

	public static String md5(String string, String salt, boolean toUppercase) {
		StringBuffer data = new StringBuffer();
		if (StringUtils.isNotBlank(salt)) {
			data.append(salt);
		}
		if (StringUtils.isNotBlank(string)) {
			data.append(string);
		}
		String hash = DigestUtils.md5Hex(data.toString());
		return toUppercase ? hash.toUpperCase() : hash;
	}
}
