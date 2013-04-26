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
package com.netdimensions.client;

import static org.apache.commons.lang3.StringUtils.defaultString;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.w3c.dom.Element;

import com.netdimensions.client.types.EnrollmentRequest;
import com.netdimensions.client.types.Module;
import com.netdimensions.client.types.Record;
import com.netdimensions.util.Dates;
import com.netdimensions.util.Function;

public final class Commands {
	// Suppress default constructor for noninstantiability
	private Commands() {
		throw new AssertionError();
	}

	public static Command<List<Article>> getUserNews(final String onBehalfOf) {
		final List<NameValuePair> parameters = new ArrayList<NameValuePair>();

		if (onBehalfOf != null) {
			parameters.add(new BasicNameValuePair("onBehalfOf", onBehalfOf));
		}

		return new Command<List<Article>>("userNews", false, parameters,
				Article.PARSER);
	}

	public static Command<List<Article>> getUserNews() {
		return getUserNews(null);
	}

	public static Command<List<Record>> getEnrollments(final String onBehalfOf) {
		final List<NameValuePair> parameters = new ArrayList<NameValuePair>();

		if (onBehalfOf != null) {
			parameters.add(new BasicNameValuePair("onBehalfOf", onBehalfOf));
		}

		return new Command<List<Record>>("enrollments", false, parameters,
				Record.PARSER);
	}

	public static Command<List<Record>> getEnrollments() {
		return getEnrollments(null);
	}

	public static Command<List<Record>> getRecords(final String onBehalfOf) {
		final List<NameValuePair> parameters = new ArrayList<NameValuePair>();

		if (onBehalfOf != null) {
			parameters.add(new BasicNameValuePair("onBehalfOf", onBehalfOf));
		}

		return new Command<List<Record>>("records", false, parameters,
				Record.PARSER);
	}

	public static Command<List<Record>> getRecords() {
		return getRecords(null);
	}

	public static Command<List<EnrollmentRequest>> getEnrollmentRequests(
			final boolean inbox, final Date since, final String filter,
			final String onBehalfOf) {
		return new Command<List<EnrollmentRequest>>("enrollment-requests",
				false, com.netdimensions.util.Collections.concatenatedList(
						inbox ? Collections
								.singletonList(new BasicNameValuePair("inbox",
										Boolean.toString(true))) : Collections
								.<NameValuePair> emptyList(), Arrays.asList(
								new BasicNameValuePair("since", Dates
										.html5String(since)),
								new BasicNameValuePair("filter",
										defaultString(filter)),
								new BasicNameValuePair("on-behalf-of",
										defaultString(onBehalfOf)),
								new BasicNameValuePair("format", "xml"))),
				EnrollmentRequest.PARSER);
	}

	public static Command<Void> approveEnrollmentRequest(
			final String requestId, final String stepId) {
		return new Command<Void>("enrollment-request-approver", true,
				Arrays.asList(new BasicNameValuePair("request-id",
						defaultString(requestId)), new BasicNameValuePair(
						"step-id", defaultString(stepId)),
						new BasicNameValuePair("format", "xml")),
				new Function<Element, Void>() {
					@Override
					public final Void value(final Element e) {
						return null;
					}
				});
	}

	public static Command<Void> denyEnrollmentRequest(final String requestId,
			final String stepId, final String comments) {
		return new Command<Void>("enrollment-request-denier", true,
				Arrays.asList(new BasicNameValuePair("request-id",
						defaultString(requestId)), new BasicNameValuePair(
						"step-id", defaultString(stepId)),
						new BasicNameValuePair("comments",
								defaultString(comments)),
						new BasicNameValuePair("format", "xml")),
				new Function<Element, Void>() {
					@Override
					public final Void value(final Element e) {
						return null;
					}
				});
	}

	public static Command<List<EnrollmentRequest>> getEnrollmentRequests(
			boolean inbox, Date since, String filter) {
		return getEnrollmentRequests(inbox, since, filter, null);
	}

	public static Command<Module> getModule(final String id,
			final String onBehalfOf) {
		final List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		parameters.add(new BasicNameValuePair("id", id));

		if (onBehalfOf != null) {
			parameters.add(new BasicNameValuePair("onBehalfOf", onBehalfOf));
		}

		return new Command<Module>("module", false, parameters,
				new Function<Element, Module>() {
					@Override
					public final Module value(final Element arg) {
						return Module.valueOf(arg);
					}
				});
	}
}
