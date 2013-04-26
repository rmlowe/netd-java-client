/*
 *
 * Copyright (c) 1999-2013 NetDimensions Ltd.
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

import static com.netdimensions.util.XPathUtils.string;

import java.util.List;

import org.w3c.dom.Node;

import com.netdimensions.util.XPathUtils;

public final class Module {
	public final String id;
	public final String title;
	public final List<?> sessions;

	public Module(final String id, final String title, final List<?> sessions) {
		this.id = id;
		this.title = title;
		this.sessions = sessions;
	}

	public static Module valueOf(final Node node) {
		return new Module(string("id", node), string("title", node),
				XPathUtils.nodes("session", node));
	}
}
