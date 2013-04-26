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

public final class Session {
	public final Module module;
	public final String id;

	public Session(final Module module, final String id) {
		this.module = module;
		this.id = id;
	}
}
