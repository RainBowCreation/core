package net.rainbowcreation.core.proxies.events

import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.player.ServerConnectedEvent as VelocityServerConnectedEvent
import net.kyori.adventure.text.Component
import net.md_5.bungee.api.chat.TextComponent
import net.md_5.bungee.api.event.ServerSwitchEvent as BungeeServerSwitchEvent
import net.md_5.bungee.api.plugin.Listener
import net.md_5.bungee.event.EventHandler

/**
 * Listens for proxy-level events for both BungeeCord and Velocity.
 */
class ProxyListener : Listener {

    /**
     * BungeeCord event handler for when a player finishes switching servers.
     */
    @EventHandler
    fun onServerSwitch(event: BungeeServerSwitchEvent) {
        val player = event.player
        player.sendMessage(TextComponent("§eYou connected to the §a${player.server.info.name} §eserver."))
    }

    /**
     * Velocity event handler for when a player connects to a new server.
     */
    @Subscribe
    fun onServerConnected(event: VelocityServerConnectedEvent) {
        val player = event.player
        player.sendMessage(Component.text("§eYou connected to the §a${event.server.serverInfo.name} §eserver."))
    }
}