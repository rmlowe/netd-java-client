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
package com.netdimensions.sample;

import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.http.auth.UsernamePasswordCredentials;

import com.netdimensions.client.Client;
import com.netdimensions.client.Commands;
import com.netdimensions.client.types.Record;

public class Enrollments {
	public static void main(String[] args) throws IOException {
		final Client client = new Client(args[0],
				new UsernamePasswordCredentials(args[1], args[2]));

		try {
			// This comment added on Dell Studio
			// This comment also added on Dell Studio
			// Third comment added on Dell Studio
			final List<Record> enrollments = client.send(Commands
					.getEnrollments());
			for (Record e : sorted(enrollments, new Comparator<Record>() {
				@Override
				public int compare(Record o1, Record o2) {
					return o2.enrollmentDate.compareTo(o1.enrollmentDate);
				}
			})) {
				System.out.println(e.learningModule.title
						+ " ("
						+ DateFormat.getDateTimeInstance().format(
								e.enrollmentDate) + ")");
			}
		} finally {
			client.close();
		}
	}

	private static <T> List<T> sorted(final List<T> list, final Comparator<T> c) {
		final List<T> result = new ArrayList<T>(list);
		Collections.sort(result, c);
		return result;
	}
}
