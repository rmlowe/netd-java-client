/*
 *
 * Copyright (c) 1999-2011 NetDimensions Ltd.
 *
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * NetDimensions Ltd. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with NetDimensions.
 */
package com.netdimensions.util;

import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import org.junit.Assert;
import org.junit.Test;

public final class DatesTest {
	@Test
	public final void testHtml5StringNull() {
		Assert.assertEquals("", Dates.html5String(null));
	}

	@Test
	public final void testHtml5StringNotNull() {
		final Calendar cal = Calendar.getInstance(
				TimeZone.getTimeZone("Asia/Hong_Kong"), Locale.ENGLISH);
		cal.clear();
		cal.set(2011, Calendar.NOVEMBER, 14, 14, 49, 37);
		Assert.assertEquals("2011-11-14T06:49Z",
				Dates.html5String(cal.getTime()));
	}

	@Test
	public final void testHtml5StringAnotherNotNull() {
		final Calendar cal = Calendar.getInstance(
				TimeZone.getTimeZone("America/Chicago"), Locale.ENGLISH);
		cal.clear();
		cal.set(1973, Calendar.JANUARY, 16, 14, 53, 2);
		Assert.assertEquals("1973-01-16T20:53Z",
				Dates.html5String(cal.getTime()));
	}
}
