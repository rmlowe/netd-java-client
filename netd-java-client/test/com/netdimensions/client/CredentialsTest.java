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

	@Test
	public final void testBasicToString() {
		Assert.assertEquals("Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ==", Credentials.basic("Aladdin", "open sesame").toString());
	}
}
