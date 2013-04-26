/*
 *
 * Copyright (c) 1999-2011 NetDimensions Ltd.
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
import java.util.List;

import com.netdimensions.client.Client;
import com.netdimensions.client.Commands;
import com.netdimensions.client.types.EnrollmentRequest;

public class EnrollmentRequestsHandler {
	public static void main(String[] args) throws IOException {
		Client myClient = new Client(
				"http://preview.netdimensions.com/preview/", "rob", "t0p5ecret");
		List<EnrollmentRequest> requests = myClient.send(Commands
				.getEnrollmentRequests(true, null, ""));

		for (EnrollmentRequest request : requests) {
			if (request.reason.toLowerCase().contains("please")) {
				myClient.send(Commands.approveEnrollmentRequest(request.id,
						request.step.id));
			} else {
				myClient.send(Commands.denyEnrollmentRequest(request.id,
						request.step.id, "Didn't ask nicely"));
			}
		}
	}
}
