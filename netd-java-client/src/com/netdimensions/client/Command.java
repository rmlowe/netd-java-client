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

import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.w3c.dom.Element;

import com.netdimensions.util.Function;

public final class Command<T> {
	final String action;
	public final boolean post;
	final List<? extends NameValuePair> parameters;
	final Function<Element, T> responseParser;

	public Command(final String action, final boolean post,
			final List<? extends NameValuePair> parameters,
			final Function<Element, T> responseParser) {
		this.action = action;
		this.post = post;
		this.parameters = parameters;
		this.responseParser = responseParser;
	}

	final String url() {
		return action + "?" + URLEncodedUtils.format(parameters, "UTF-8");
	}
}
