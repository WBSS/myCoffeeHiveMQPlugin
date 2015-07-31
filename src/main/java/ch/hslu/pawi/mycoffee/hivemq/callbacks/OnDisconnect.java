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

import com.dcsquare.hivemq.spi.callback.events.OnDisconnectCallback;
import com.dcsquare.hivemq.spi.security.ClientData;

/**
 * Class for handling client disconnections.
 * @author Philipp Rupp
 */
public class OnDisconnect implements OnDisconnectCallback {

	private final DataService dataService;

	@Inject
	public OnDisconnect(final DataService dataService) {
		this.dataService = dataService;
	}

	/**
	 * Callback is called when the client is disconnecting from broker.
	 * Updates the client state to disconnected in the database.
	 * @param clientData client data
	 * @param arg1
	 */
	@Override
	public void onDisconnect(final ClientData clientData, final boolean arg1) {
		dataService.changeClientStatus(clientData.getClientId(), false);
	}
}
