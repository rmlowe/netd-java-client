/*
 *
 * Copyright (c) 1999-2012 NetDimensions Ltd.
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

public final class UnauthorizedException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public final String reasonPhrase;

	public UnauthorizedException(final String reasonPhrase) {
		super(reasonPhrase);
		this.reasonPhrase = reasonPhrase;
	}

}
