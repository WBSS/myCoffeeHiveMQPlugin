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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.hslu.pawi.mycoffee.hivemq.datalayer.DataService;

import com.dcsquare.hivemq.spi.callback.CallbackPriority;
import com.dcsquare.hivemq.spi.callback.exception.AuthenticationException;
import com.dcsquare.hivemq.spi.callback.security.OnAuthenticationCallback;
import com.dcsquare.hivemq.spi.security.ClientCredentialsData;

/**
 * Class for handling new client connections.
 * Authenticate clients and saves the client state to the database.
 * @author Philipp Rupp
 */
public class OnConnect implements OnAuthenticationCallback {

	private static Logger log = LoggerFactory.getLogger(OnConnect.class);

	private final DataService dataService;

	@Inject
	public OnConnect(final DataService dataService) {
		this.dataService = dataService;
	}

	/**
	 * Validates the user data credentials.
	 * If correct the client gets access to the broker and
	 * and the client state will be set to connected in the database.
	 * @param clientData client data
	 * @return <code>true</code> if user data is correct
	 * 			<code>false</code> otherwise
	 * @throws AuthenticationException
	 */
	@Override
	public Boolean checkCredentials(final ClientCredentialsData clientData) throws AuthenticationException {
		if (clientData.getUsername().isPresent() && clientData.getPassword().isPresent()) {
			return lookupInDB(clientData);
		}
		return false;
	}

	/**
	 * Verifies user data with db saved data.
	 * If correct the client can connect to the broker
	 * @param clientData client data
	 * @return true if user data is correct
	 * 		   false otherwise
	 */
	private boolean lookupInDB(final ClientCredentialsData clientData) {
		final String password = dataService.getPasswordByUsername(clientData.getUsername().get());

		if (password == null) {
			log.info("User {} not registered", clientData.getUsername().get());
			return false;
		}
		//NEVER EVER even dream about doing this in production
		//ALWAYS use a proper hashing mechanism and use salts.
		//Read here how to do this properly: https://crackstation.net/hashing-security.htm
		else if (clientData.getPassword().get().equals(password)) {
			dataService.changeClientStatus(clientData.getClientId(), true);
			return true;
		}

		log.info("Password for User {} wrong", clientData.getUsername().get());

		return false;
	}

	@Override
	public int priority() {
		return CallbackPriority.HIGH;
	}
}
