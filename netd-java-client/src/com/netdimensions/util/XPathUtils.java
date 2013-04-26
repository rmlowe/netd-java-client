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
package com.netdimensions.util;

import java.util.AbstractList;
import java.util.List;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public final class XPathUtils {
	private static final class EvaluationContext {
		private final String expression;
		private final Object item;

		private EvaluationContext(final String expression, final Object item) {
			this.expression = expression;
			this.item = item;
		}

		private final String string() throws XPathExpressionException {
			return XPathUtils.newXPath().evaluate(expression, item);
		}

		private static List<Node> nodes(final NodeList nl) {
			return new AbstractList<Node>() {
				@Override
				public final Node get(final int index) {
					if (index < 0 || index >= nl.getLength()) {
						throw new IndexOutOfBoundsException();
					} else {
						return nl.item(index);
					}
				}

				@Override
				public final int size() {
					return nl.getLength();
				}
			};
		}

		private final List<Node> nodes() throws XPathExpressionException {
			return nodes((NodeList) XPathUtils.newXPath().evaluate(expression,
					item, XPathConstants.NODESET));
		}

		private final Node node() throws XPathExpressionException {
			return (Node) XPathUtils.newXPath().evaluate(expression, item,
					XPathConstants.NODE);
		}
	}

	private static interface Evaluator<T> {
		T value(EvaluationContext ec) throws XPathExpressionException;
	}

	// Suppress default constructor for noninstantiability
	private XPathUtils() {
		throw new AssertionError();
	}

	public static List<Node> nodes(final String expression, final Node item) {
		return XPathUtils.value(new XPathUtils.Evaluator<List<Node>>() {
			@Override
			public final List<Node> value(final XPathUtils.EvaluationContext ec)
					throws XPathExpressionException {
				return ec.nodes();
			}
		}, new XPathUtils.EvaluationContext(expression, item));
	}

	public static Node node(final String expression, final Node item) {
		return XPathUtils.value(new XPathUtils.Evaluator<Node>() {
			@Override
			public final Node value(final XPathUtils.EvaluationContext ec)
					throws XPathExpressionException {
				return ec.node();
			}
		}, new XPathUtils.EvaluationContext(expression, item));
	}

	private static <T> T value(final XPathUtils.Evaluator<T> evaluator,
			final EvaluationContext ec) {
		try {
			return evaluator.value(ec);
		} catch (final XPathExpressionException e) {
			throw new IllegalArgumentException("Illegal XPath expression: '"
					+ ec.expression + "'");
		}
	}

	private static XPath newXPath() {
		return XPathFactory.newInstance().newXPath();
	}

	public static String string(final String expression, final Node item) {
		return value(new Evaluator<String>() {
			@Override
			public final String value(final EvaluationContext ec)
					throws XPathExpressionException {
				return ec.string();
			}
		}, new EvaluationContext(expression, item));
	}
}
