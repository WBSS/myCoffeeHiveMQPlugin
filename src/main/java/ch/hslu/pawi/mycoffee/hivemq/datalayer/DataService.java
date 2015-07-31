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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Provider;

/**
 * Data layer class of the myCoffee project.
 * Saves and reads entities from the MySQL database.
 * @author Philipp Rupp
 */
@Singleton
public class DataService {

	private static Logger log = LoggerFactory.getLogger(DataService.class);

	// SQL Queries
	private static final String selectPasswordFromUser = "SELECT password from `Users` where username = ?";
	private static final String selectIdFromUser = "SELECT id from `Users` where username = ?";
	private static final String persistClientState = "INSERT INTO `Clients` (client, status) VALUES (?,?) ON DUPLICATE KEY UPDATE status = ?";

	private final Provider<Connection> connectionProvider;

	@Inject
	public DataService(final Provider<Connection> connectionProvider) {
		this.connectionProvider = connectionProvider;
	}

	/**
	 * Reads the password of a user from the database.
	 * @param username user name which password is required.
	 * @return password of the user. can be <code>null</code>
	 */
	public String getPasswordByUsername(final String username) {
		final Connection connection = connectionProvider.get();

		String password = null;

		try {
			final PreparedStatement statement = connection.prepareStatement(selectPasswordFromUser);
			statement.setString(1, username);

			final ResultSet resultSet = statement.executeQuery();

			if (!resultSet.isBeforeFirst()) {
				log.warn("User {} was not found in the database", username);
				return null;
			}
			resultSet.next();
			password = resultSet.getString("password");

		} catch (final SQLException e) {
			log.error("An SQL error occured", e);
		} finally {
			try {
				connection.close();
			} catch (final SQLException e) {
				log.error("Error while giving back connection to connection pool", e);
			}
		}
		return password;
	}

	/**
	 * Reads the Id of a user form the database.
	 * @param username user name which Id is required.
	 * @return Id if the user. Can be <code>null</code>
	 */
	public Integer getIdByUsername(final String username) {
		final Connection connection = connectionProvider.get();

		Integer id = null;

		try {
			final PreparedStatement statement = connection.prepareStatement(selectIdFromUser);
			statement.setString(1, username);

			final ResultSet resultSet = statement.executeQuery();

			if (!resultSet.isBeforeFirst()) {
				log.warn("User {} was not found in the database", username);
				return null;
			}
			resultSet.next();
			id = resultSet.getInt("id");

		} catch (final SQLException e) {
			log.error("An SQL error occured", e);
		} finally {
			try {
				connection.close();
			} catch (final SQLException e) {
				log.error("Error while giving back connection to connection pool", e);
			}
		}
		return id;
	}
	
	/**
	 * Changes the client state in the database.
	 * If the client does not exist, a new entry in the database is applied.
	 * Otherwise the state of the client will be updated.
	 * @param clientID Id of the client
	 * @param connected <code>true</code> if the client is connected to the broker
	 *         			<code>false</code> otherwise
	 * @return <code>true</code> if the db update was successful
	 *         <code>false</code> otherwise
	 */
	public boolean changeClientStatus(final String clientID, final boolean connected) {
		final Connection connection = connectionProvider.get();
		try {
			final PreparedStatement preparedStatement = connection.prepareStatement(persistClientState);
			preparedStatement.setString(1, clientID);
			preparedStatement.setInt(2, connected ? 1 : 0);
			preparedStatement.setInt(3, connected ? 1 : 0);

			preparedStatement.execute();
			preparedStatement.close();
		} catch (final SQLException e) {
			log.error("An error occured while preparing the SQL statement", e);
			return false;
		} finally {
			try {
				connection.close();
			} catch (final SQLException e) {
				log.error("An error occured while giving back a connection to the connection pool");
			}
		}
		return true;
	}

}
