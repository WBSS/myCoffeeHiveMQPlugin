package ch.hslu.pawi.mycoffee.hivemq.plugin;

import static com.dcsquare.hivemq.spi.config.Configurations.noConfigurationNeeded;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.inject.Singleton;

import org.apache.commons.configuration.AbstractConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.hslu.pawi.mycoffee.hivemq.callbacks.OnMessageReceived;

import com.dcsquare.hivemq.spi.HiveMQPluginModule;
import com.dcsquare.hivemq.spi.PluginEntryPoint;
import com.dcsquare.hivemq.spi.plugin.meta.Information;
import com.google.inject.Provider;
import com.google.inject.Provides;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * This class is used for loading the actual plugin.
 * It defines where the plugin entry point is, when the HiveMQ starts up.
 * In addition any plugin configurations are loaded.
 * For more information see:
 * http://www.hivemq.com/docs/plugins/2.1.0/#plugin-project-structure
 * 
 * @author Philipp Rupp
 */

@Information(name = "myCoffee HiveMQ Plugin", version = "0.0.1", author = "Philipp Rupp", description = "Plugin for the myCoffee project. Features:\n- User authentification\n - Persistence of connection information from all connected clients")
public class DatabasePluginModule extends HiveMQPluginModule {

	private static Logger log = LoggerFactory.getLogger(OnMessageReceived.class);

	@Provides
	public Connection provideConnection(final HikariDataSource ds) throws SQLException {
		return ds.getConnection();
	}

	@Provides
	@Singleton
	public HikariDataSource provideConnectionPool() {
		log.info("Connecting");
		final HikariConfig config = new HikariConfig();

		final Properties prop = new Properties();
		InputStream input = null;

		try {
			input = new FileInputStream("/opt/hivemq/plugins/dbconfig.properties");
			prop.load(input);

			config.setMaximumPoolSize(15);
			config.setDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
			config.addDataSourceProperty("serverName", prop.getProperty("serverName"));
			config.addDataSourceProperty("port", prop.getProperty("port"));
			config.addDataSourceProperty("databaseName", prop.getProperty("databaseName"));
			config.addDataSourceProperty("user", prop.getProperty("user"));
			config.addDataSourceProperty("password", prop.getProperty("password"));
		} catch (final IOException ex) {
			log.error(ex.getMessage());
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (final IOException e) {
					log.error(e.getMessage());
				}
			}
		}

		log.info("Connected");
		return new HikariDataSource(config);
	}

	@Override
	public Provider<Iterable<? extends AbstractConfiguration>> getConfigurations() {
		//You probably want an external file configuration
		return noConfigurationNeeded();
	}

	@Override
	protected void configurePlugin() {
	}

	@Override
	protected Class<? extends PluginEntryPoint> entryPointClass() {
		return DatabasePlugin.class;
	}
}
