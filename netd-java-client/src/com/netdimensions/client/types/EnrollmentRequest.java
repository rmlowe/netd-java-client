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

import static com.netdimensions.util.XPathUtils.nodes;
import static com.netdimensions.util.XPathUtils.string;

import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.netdimensions.util.Collections;
import com.netdimensions.util.Function;
import com.netdimensions.util.XPathUtils;

public final class EnrollmentRequest {
	public static final Function<Element, List<EnrollmentRequest>> PARSER = new Function<Element, List<EnrollmentRequest>>() {
		@Override
		public final List<EnrollmentRequest> value(final Element element) {
			return enrollmentRequests(nodes("request", element));
		}
	};

	public final String id;
	public final Person from;
	public final Session session;
	public final String reason;
	public final Step step;

	public EnrollmentRequest(final String id, final Person from,
			final Session session, final String reason, final Step step) {
		this.id = id;
		this.from = from;
		this.session = session;
		this.reason = reason;
		this.step = step;
	}

	private static List<EnrollmentRequest> enrollmentRequests(
			final List<Node> nodes) {
		return Collections.mappedList(nodes,
				new Function<Node, EnrollmentRequest>() {
					@Override
					public final EnrollmentRequest value(final Node arg) {
						return enrollmentRequest(arg);
					}
				});
	}

	private static EnrollmentRequest enrollmentRequest(final Node node) {
		return new EnrollmentRequest(string("id", node), new Person(string(
				"from/id", node), string("from/family", node), string(
				"from/given", node)), new Session(Module.valueOf(XPathUtils
				.node("session/module", node)), nodes("session/id", node)
				.isEmpty() ? null : string("session/id", node)), string(
				"reason", node), new Step(string("step/id", node)));
	}
}
