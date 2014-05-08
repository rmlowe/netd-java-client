/*
 *
 * Copyright (c) 1999-2014 NetDimensions Ltd.
 *
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * NetDimensions Ltd. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with NetDimensions.
 */
package com.netdimensions.client;

import org.junit.Assert;
import org.junit.Test;

public final class CredentialsTest {

	/**
	 * http://tools.ietf.org/html/rfc2617#section-2
	 */
	@Test
	public final void testBasicToString() {
		Assert.assertEquals("Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ==", Credentials.basic("Aladdin", "open sesame").toString());
	}

	/**
	 * http://tools.ietf.org/html/rfc6750#section-2.1
	 */
	@Test
	public final void testBearerToString() {
		Assert.assertEquals("Bearer mF_9.B5f-4.1JqM", Credentials.bearer("mF_9.B5f-4.1JqM").toString());
	}
}
