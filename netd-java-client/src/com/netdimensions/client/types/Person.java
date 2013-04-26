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
package com.netdimensions.client.types;

public final class Person {
	public final String id;
	public final String familyName;
	public final String givenName;

	public Person(final String id, final String familyName,
			final String givenName) {
		this.id = id;
		this.familyName = familyName;
		this.givenName = givenName;
	}
}
