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

import java.sql.Date;

/**
 * Model for database table 'Messages'.
 * @author Philipp Rupp
 */
public class MessageEntity {

	private int id;
	private byte[] message;
	private String topic;
	private int qos;
	private String client;
	private Date date;

	public MessageEntity() {

	}

	public int getId() {
		return id;
	}

	public void setId(final int id) {
		this.id = id;
	}

	public byte[] getMessage() {
		return message;
	}

	public void setMessage(final byte[] message) {
		this.message = message;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(final String topic) {
		this.topic = topic;
	}

	public int getQos() {
		return qos;
	}

	public void setQos(final int qos) {
		this.qos = qos;
	}

	public String getClient() {
		return client;
	}

	public void setClient(final String client) {
		this.client = client;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(final Date date) {
		this.date = date;
	}

}
