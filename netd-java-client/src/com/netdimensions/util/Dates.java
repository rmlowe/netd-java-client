package com.netdimensions.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public final class Dates {
	// Suppress default constructor for noninstantiability
	private Dates() {
		throw new AssertionError();
	}

	public static String html5String(final Date date) {
		return date == null ? "" : format().format(date);
	}

	private static SimpleDateFormat format() {
		final SimpleDateFormat result = new SimpleDateFormat(
				"yyyy-MM-dd'T'HH:mm'Z'");
		result.setTimeZone(TimeZone.getTimeZone("GMT"));
		return result;
	}
}
