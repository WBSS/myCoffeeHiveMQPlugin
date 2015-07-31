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

package ch.hslu.pawi.mycoffee.hivemq.callbacks;

import javax.inject.Inject;

import ch.hslu.pawi.mycoffee.hivemq.datalayer.DataService;

import com.dcsquare.hivemq.spi.callback.CallbackPriority;
import com.dcsquare.hivemq.spi.callback.events.OnPublishReceivedCallback;
import com.dcsquare.hivemq.spi.callback.exception.OnPublishReceivedException;
import com.dcsquare.hivemq.spi.message.PUBLISH;
import com.dcsquare.hivemq.spi.security.ClientData;

/**
 * Class to add userId to the payload
 * @author Philipp Rupp
 */
public class OnMessageReceived implements OnPublishReceivedCallback {

	private final DataService dataService;

	@Inject
	public OnMessageReceived(final DataService dataService) {
		this.dataService = dataService;
	}

	/**
	 * Invoked every received MQTT message.
	 * Gets userId from database and adds it to the message payload
	 * @param publish received MQTT message
	 * @param clientData information of publishers message
	 * @throws OnPublishReceivedException
	 */
	@Override
	public void onPublishReceived(final PUBLISH publish, final ClientData clientData) throws OnPublishReceivedException {
		
		if (!publish.getTopic().endsWith("/")) {
			// Get userId from DB
			int userId = dataService.getIdByUsername(clientData.getUsername().get());
			
			// Add userId to payload
			publish.setPayload(("userId=" + userId + "\n" + new String(publish.getPayload())).getBytes());
		}
	}

	@Override
	public int priority() {
		return CallbackPriority.HIGH;
	}
}
