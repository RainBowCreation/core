package net.rainbowcreation.core.core.common.commands

import net.rainbowcreation.core.core.common.AbstractPlugin
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

/**
 * The main command executor for the plugin.
 * Handles the logic for the `/multiversionspluginexample` command.
 *
 * @param plugin The main instance of the plugin (can be LegacyPlugin or ModernPlugin).
 */
class MainCommand(private val plugin: AbstractPlugin) : CommandExecutor {

    /**
     * Executes the given command, returning its success.
     *
     * @param sender Source of the command.
     * @param command Command which was executed.
     * @param label Alias of the command which was used.
     * @param args Passed command arguments.
     * @return true if a valid command, otherwise false.
     */
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        // Construct a message showing the plugin name and version from the plugin.yml.
        val message = "${ChatColor.GOLD}${plugin.description.name} ${ChatColor.GRAY}version ${plugin.description.version}"

        // Send the message to the command sender.
        sender.sendMessage(message)

        return true
    }
}