package net.rainbowcreation.core.proxies.bungee

import net.md_5.bungee.api.chat.TextComponent
import net.md_5.bungee.api.connection.Server
import net.md_5.bungee.api.event.PluginMessageEvent
import net.md_5.bungee.api.plugin.Listener
import net.md_5.bungee.api.plugin.Plugin
import net.md_5.bungee.event.EventHandler
import net.rainbowcreation.core.api.messaging.PluginChannels
import net.rainbowcreation.core.api.messaging.requests.ServerConnectRequest
import net.rainbowcreation.core.proxies.MainProxy
import net.rainbowcreation.core.proxies.commands.ProxyCommand
import net.rainbowcreation.core.proxies.events.ProxyListener
import net.rainbowcreation.core.proxies.messaging.MessageDeserializer

/**
 * The main logic class for the BungeeCord environment. It is initialized
 * by MainProxy when BungeeCord is detected.
 *
 * @param proxy The instance of the plugin loaded by BungeeCord.
 */
class BungeePlugin(private val proxy: MainProxy) : Plugin(), Listener {

    override fun onEnable() {
        // 1. Register the plugin messaging channel
        proxy.proxy.registerChannel(PluginChannels.BUKKIT_TO_PROXY)

        // 2. Register event listeners
        proxy.proxy.pluginManager.registerListener(proxy, this)
        proxy.proxy.pluginManager.registerListener(proxy, ProxyListener())

        // 3. Register commands
        proxy.proxy.pluginManager.registerCommand(proxy, ProxyCommand(proxy.proxy))

        proxy.logger.info("Proxy plugin enabled for BungeeCord.")
    }

    /**
     * Handles incoming plugin messages from the core plugin.
     */
    @EventHandler
    fun onPluginMessage(event: PluginMessageEvent) {
        // Ensure the message is on our channel and is coming from a server
        if (event.tag != PluginChannels.BUKKIT_TO_PROXY || event.sender !is Server) {
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
            proxy.logger.warning("Failed to deserialize a plugin message: ${e.message}")
        }
    }

    /**
     * Processes a request to connect a player to another server.
     */
    private fun handleServerConnectRequest(request: ServerConnectRequest) {
        val player = proxy.proxy.getPlayer(request.playerName)
        val server = proxy.proxy.getServerInfo(request.targetServer)

        if (player == null) {
            proxy.logger.warning("Received a server connect request for an unknown player: ${request.playerName}")
            return
        }

        if (server == null) {
            player.sendMessage(TextComponent("§cError: The server '${request.targetServer}' does not exist."))
            return
        }

        // Connect the player to the target server
        player.connect(server)
    }
}