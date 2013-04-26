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

import java.util.Date;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.netdimensions.util.Collections;
import com.netdimensions.util.Function;
import com.netdimensions.util.XPathUtils;

public final class Record {
	public static final Function<Element, List<Record>> PARSER = new Function<Element, List<Record>>() {
		@Override
		public final List<Record> value(final Element e) {
			return Collections.mappedList(
					XPathUtils.nodes("trainingRecord", e),
					new Function<Node, Record>() {
						@Override
						public final Record value(final Node arg) {
							try {
								return new Record(
										Module.valueOf(XPathUtils.node(
												"learningModule", arg)),
										DatatypeFactory
												.newInstance()
												.newXMLGregorianCalendar(
														XPathUtils
																.string("enrollmentDate",
																		arg))
												.toGregorianCalendar()
												.getTime());
							} catch (final DatatypeConfigurationException e) {
								throw new RuntimeException(e);
							}
						}
					});
		}
	};
	public final Module learningModule;
	public final Date enrollmentDate;

	public Record(Module learningModule, Date enrollmentDate) {
		this.learningModule = learningModule;
		this.enrollmentDate = enrollmentDate;
	}
}
