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

import com.google.common.base.Charsets;
import com.google.common.io.BaseEncoding;

public abstract class Credentials {
	private Credentials() {
	}

	public static Credentials basic(final String username, final String password) {
		return new Credentials() {

			@Override
			public final String toString() {
				return "Basic " + BaseEncoding.base64().encode((username + ":" + password).getBytes(Charsets.UTF_8));
			}
		};
	}
}
