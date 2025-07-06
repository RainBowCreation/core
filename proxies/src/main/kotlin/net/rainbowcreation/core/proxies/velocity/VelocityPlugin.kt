package net.rainbowcreation.core.proxies.velocity

import com.google.inject.Inject
import com.velocitypowered.api.command.CommandManager
import com.velocitypowered.api.command.CommandMeta
import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.connection.PluginMessageEvent
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent
import com.velocitypowered.api.plugin.Plugin
import com.velocitypowered.api.proxy.ProxyServer
import com.velocitypowered.api.proxy.messages.MinecraftChannelIdentifier
import com.velocitypowered.api.proxy.server.RegisteredServer
import net.kyori.adventure.text.Component
import net.rainbowcreation.core.api.messaging.PluginChannels
import net.rainbowcreation.core.api.messaging.requests.ServerConnectRequest
import net.rainbowcreation.core.proxies.commands.ProxyCommand
import net.rainbowcreation.core.proxies.events.ProxyListener
import net.rainbowcreation.core.proxies.messaging.MessageDeserializer
import org.slf4j.Logger

/**
 * The main logic class for the Velocity environment.
 * Velocity uses @Inject to provide necessary instances like the logger and server.
 */
@Plugin(
    id = "rainbowcreation-velocity",
)
class VelocityPlugin @Inject constructor(
    private val server: ProxyServer,
    private val logger: Logger,
    private val commandManager: CommandManager
) {

    private val incomingChannel = MinecraftChannelIdentifier.create(
        PluginChannels.BUKKIT_TO_PROXY.split(":")[0],
        PluginChannels.BUKKIT_TO_PROXY.split(":")[1]
    )

    /**
     * This method is called by Velocity when the proxy is initializing.
     */
    @Subscribe
    fun onProxyInitialization(event: ProxyInitializeEvent) {
        // 1. Register the plugin messaging channel
        server.channelRegistrar.register(incomingChannel)

        // 2. Register event listeners
        server.eventManager.register(this, this)
        server.eventManager.register(this, ProxyListener())

        // 3. Register commands
        val meta: CommandMeta = commandManager.metaBuilder("proxybroadcast").build()
        commandManager.register(meta, ProxyCommand(server))

        logger.info("Proxy plugin enabled for Velocity.")
    }

    /**
     * Handles incoming plugin messages from the core plugin.
     */
    @Subscribe
    fun onPluginMessage(event: PluginMessageEvent) {
        // Ensure the message is on our channel and is coming from a server
        if (event.identifier != incomingChannel || event.source !is RegisteredServer) {
            return
        }

        try {
            // Deserialize the byte data back into a usable object
            val message = MessageDeserializer.deserialize(event.data)

            // Check if the message is a ServerConnectRequest and handle it
            if (message is ServerConnectRequest) {
                handleServerConnectRequest(message)
            }
        } catch (e: Exception) {
            logger.warn("Failed to deserialize a plugin message: ${e.message}")
        }
    }

    /**
     * Processes a request to connect a player to another server.
     */
    private fun handleServerConnectRequest(request: ServerConnectRequest) {
        val playerOptional = server.getPlayer(request.playerName)
        if (!playerOptional.isPresent) {
            logger.warn("Received a server connect request for an unknown player: ${request.playerName}")
            return
        }

        val player = playerOptional.get()
        val serverOptional = server.getServer(request.targetServer)

        if (!serverOptional.isPresent) {
            player.sendMessage(Component.text("§cError: The server '${request.targetServer}' does not exist."))
            return
        }

        // Connect the player to the target server
        player.createConnectionRequest(serverOptional.get()).fireAndForget()
    }
}