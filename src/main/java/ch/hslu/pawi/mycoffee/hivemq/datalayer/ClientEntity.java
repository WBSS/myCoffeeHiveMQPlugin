/* 
 * Copyright 2015 W.B.S.S GmbH Zurich.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not 
 * use this file except in compliance with the License. You may obtain a copy 
 * of the License at 
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0 
 *   
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT 
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the 
 * License for the specific language governing permissions and limitations 
 * under the License.
 * 
 */

package ch.hslu.pawi.mycoffee.hivemq.datalayer;

import java.util.Date;

/**
 * Model for database table 'Clients'.
 * @author Philipp Rupp
 */
public class ClientEntity {

	private String client;
	private byte status;
	private Date timestamp;
	private byte[] payload;

	public ClientEntity() {

	}

	public String getClient() {
		return client;
	}

	public void setClient(final String client) {
		this.client = client;
	}

	public byte getStatus() {
		return status;
	}

	public void setStatus(final byte status) {
		this.status = status;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(final Date timestamp) {
		this.timestamp = timestamp;
	}

	public byte[] getPayload() {
		return payload;
	}

	public void setPayload(final byte[] payload) {
		this.payload = payload;
	}
}
