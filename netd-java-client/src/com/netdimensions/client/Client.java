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

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public final class Client {
	private final DefaultHttpClient client = new DefaultHttpClient();
	private final URI url;

	public Client(final String url,
			final UsernamePasswordCredentials credentials) {
		final URI uri = URI.create(url);
		this.url = uri;

		if (credentials != null) {
			client.getCredentialsProvider().setCredentials(authScope(uri),
					credentials);
		}
	}

	public Client(final String url, final String userName, final String password) {
		this(url, new UsernamePasswordCredentials(userName, password));
	}

	public Client(final String url) {
		this(url, null);
	}

	private static AuthScope authScope(final URI url) {
		return new AuthScope(url.getHost(),
				url.getPort() == -1 ? defaultPort(url.getScheme())
						: url.getPort());
	}

	private static int defaultPort(final String scheme) {
		if (scheme.equalsIgnoreCase("https")) {
			return 443;
		} else if (scheme.equalsIgnoreCase("http")) {
			return 80;
		} else {
			throw new IllegalArgumentException("Unsupported scheme: '" + scheme
					+ "'");
		}
	}

	public final <T> T send(final Command<T> command) throws IOException {
		// Authenticate preemptively
		return command.responseParser.value(client.execute(request(command),
				new ResponseHandler<Element>() {
					@Override
					public final Element handleResponse(
							final HttpResponse response) throws IOException {
						Logger.getLogger("com.netdimensions.client").info(
								response.getStatusLine().getReasonPhrase());

						switch (response.getStatusLine().getStatusCode()) {
						case HttpStatus.SC_OK:
							return parse(response.getEntity());
						case HttpStatus.SC_UNAUTHORIZED:
							throw new UnauthorizedException(response
									.getStatusLine().getReasonPhrase());
						default:
							throw new IOException(response.getStatusLine()
									.getReasonPhrase());
						}
					}
				}, context(url)));
	}

	private <T> HttpUriRequest request(final Command<T> command) {
		if (command.post) {
			final HttpPost result = new HttpPost(resolved(command.action));
			try {
				result.setEntity(new UrlEncodedFormEntity(command.parameters,
						"UTF-8"));
				return result;
			} catch (final UnsupportedEncodingException e) {
				throw new AssertionError("UTF-8 unsupported");
			}
		} else {
			return new HttpGet(resolved(command.url()));
		}
	}

	private URI resolved(final String str) {
		return url.resolve("api/").resolve(str);
	}

	private static BasicHttpContext context(final URI url) {
		final BasicHttpContext result = new BasicHttpContext();
		result.setAttribute(ClientContext.AUTH_CACHE, authCache(url));
		return result;
	}

	private static BasicAuthCache authCache(final URI url) {
		final BasicAuthCache result = new BasicAuthCache();
		result.put(host(url), new BasicScheme());
		return result;
	}

	private static HttpHost host(final URI url) {
		return new HttpHost(url.getHost(), url.getPort(), url.getScheme());
	}

	private static Element parse(final HttpEntity entity) throws IOException {
		final InputStream in = entity.getContent();
		try {
			return DocumentBuilderFactory.newInstance().newDocumentBuilder()
					.parse(in).getDocumentElement();
		} catch (final SAXException e) {
			throw new IOException(e);
		} catch (final ParserConfigurationException e) {
			throw new RuntimeException(e);
		} finally {
			in.close();
		}
	}

	public final void close() {
		client.getConnectionManager().shutdown();
	}

	public static void main(String[] args) throws IOException {
		String url = args[0];
		String userName = args[1];
		String password = args[2];
		String onBehalfOf = args[3];
		Client client = new Client(url, userName, password);
		System.out.println("enrollments.size: "
				+ client.send(Commands.getEnrollments(onBehalfOf)).size());
		System.out.println("userNews.size: "
				+ client.send(Commands.getUserNews(onBehalfOf)).size());
		System.out.println("records.size: "
				+ client.send(Commands.getRecords(onBehalfOf)).size());
		// System.out
		// .println("module.sessions.size: "
		// + client.send(Commands
		// .getModule("2111_bls", onBehalfOf)).sessions
		// .size());
		System.out
				.println("module.sessions.size: "
						+ client.send(Commands.getModule("111_fire_safety",
								onBehalfOf)).sessions.size());
	}
}
