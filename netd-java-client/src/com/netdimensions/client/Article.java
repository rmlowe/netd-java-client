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

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.netdimensions.util.Collections;
import com.netdimensions.util.Function;
import com.netdimensions.util.XPathUtils;

public final class Article {
	public static final Function<Element, List<Article>> PARSER = new Function<Element, List<Article>>() {
		@Override
		public final List<Article> value(final Element e) {
			return Collections.mappedList(XPathUtils.nodes("channel/item", e),
					new Function<Node, Article>() {
						@Override
						public final Article value(final Node arg) {
							return new Article();
						}
					});
		}
	};
}
