package ch.hslu.pawi.mycoffee.hivemq.plugin;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import ch.hslu.pawi.mycoffee.hivemq.callbacks.OnConnect;
import ch.hslu.pawi.mycoffee.hivemq.callbacks.OnDisconnect;
import ch.hslu.pawi.mycoffee.hivemq.callbacks.OnMessageReceived;
import ch.hslu.pawi.mycoffee.hivemq.callbacks.OnShutdown;

import com.dcsquare.hivemq.spi.PluginEntryPoint;
import com.dcsquare.hivemq.spi.callback.registry.CallbackRegistry;

/**
 * This class is the entry point of the HiveMQ plugin
 * and will be called when HiveMQ starts.
 * At this place the plugin callbacks will be registerd.
 * For more information see:
 * http://www.hivemq.com/docs/plugins/2.1.0/#plugin-project-structure
 * 
 * @author Philipp Rupp
 */
public class DatabasePlugin extends PluginEntryPoint {

	private final OnMessageReceived persistMessagesCallback;
	private final OnShutdown shutdownCallback;
	private final OnConnect dbAuthenticationCallback;
	private final OnDisconnect clientControl;

	@Inject
	public DatabasePlugin(
			final OnMessageReceived persistMessagesCallback,
			final OnShutdown shutdownCallback,
			final OnConnect dbAuthenticationCallback,
			final OnDisconnect clientControl) {
		this.persistMessagesCallback = persistMessagesCallback;
		this.shutdownCallback = shutdownCallback;
		this.dbAuthenticationCallback = dbAuthenticationCallback;
		this.clientControl = clientControl;
	}

	@PostConstruct
	public void postConstruct() {

		final CallbackRegistry callbackRegistry = getCallbackRegistry();

		callbackRegistry.addCallback(persistMessagesCallback);
		callbackRegistry.addCallback(shutdownCallback);
		callbackRegistry.addCallback(dbAuthenticationCallback);
		callbackRegistry.addCallback(clientControl);

	}
}
