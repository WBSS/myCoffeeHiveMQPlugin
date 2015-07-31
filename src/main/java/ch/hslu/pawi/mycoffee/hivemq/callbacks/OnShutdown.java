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

import com.dcsquare.hivemq.spi.callback.CallbackPriority;
import com.dcsquare.hivemq.spi.callback.events.broker.OnBrokerStop;
import com.zaxxer.hikari.HikariDataSource;

/**
 * Class for cleanly exit the myCoffee HiveMQ plugins
 * by broker shutdown.
 * @author Philipp Rupp
 */
public class OnShutdown implements OnBrokerStop {

	private static Logger log = LoggerFactory.getLogger(OnShutdown.class);
	private final HikariDataSource hikariDataSource;

	@Inject
	public OnShutdown(final HikariDataSource hikariDataSource) {
		this.hikariDataSource = hikariDataSource;
	}

	/**
	 * Callback is invoked when the broker is shutting down
	 */
	@Override
	public void onBrokerStop() {
		log.info("Shutting down DB connection pool");
		hikariDataSource.shutdown();
	}

	@Override
	public int priority() {
		return CallbackPriority.LOW;
	}
}
