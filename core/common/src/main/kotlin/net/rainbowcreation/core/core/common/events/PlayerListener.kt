package net.rainbowcreation.core.core.common.events

import net.rainbowcreation.core.core.common.AbstractPlugin
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.player.PlayerJoinEvent

class PlayerListener(private val plugin: AbstractPlugin) : Listener {

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        val player = event.player
        val welcomeMessage = "${ChatColor.GREEN}Welcome to the server, ${player.name}!"

        // Use the adapter to send the welcome message.
        plugin.versionAdapter.sendActionbar(player, welcomeMessage)
    }

    @EventHandler
    fun onBlockBreak(event: BlockBreakEvent) {
        val block = event.block

        if (block.type == Material.DIAMOND_BLOCK) {
            event.isCancelled = true
            event.player.sendMessage("${ChatColor.RED}You cannot break diamond blocks!")
        }
    }
}