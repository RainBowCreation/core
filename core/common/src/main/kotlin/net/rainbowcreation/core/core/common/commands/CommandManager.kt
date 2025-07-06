package net.rainbowcreation.core.core.common.commands

import net.rainbowcreation.core.core.common.AbstractPlugin

/**
 * Manages the registration of all common commands for the plugin.
 *
 * @param plugin The main instance of the plugin (can be LegacyPlugin or ModernPlugin).
 */
class CommandManager(private val plugin: AbstractPlugin) {

    /**
     * Registers all common plugin commands.
     */
    fun registerCommands() {
        // Register the main command, passing the plugin instance to the executor.
        plugin.getCommand("rbc")?.setExecutor(MainCommand(plugin))

        // Any other commands that are shared between legacy and modern versions
        // would be registered here as well.
    }
}