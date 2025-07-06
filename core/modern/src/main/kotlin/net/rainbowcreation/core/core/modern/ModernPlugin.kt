package net.rainbowcreation.core.core.modern

import net.rainbowcreation.core.api.Api
import net.rainbowcreation.core.core.common.AbstractPlugin
import net.rainbowcreation.core.core.common.commands.CommandManager
import net.rainbowcreation.core.core.common.events.PlayerListener
import org.bukkit.plugin.ServicePriority

/**
 * The main plugin class for modern servers (1.17+).
 */
class ModernPlugin : AbstractPlugin(), Api {
    override fun onEnable() {
        // 1. Load common components (config, datasource)
        loadPlugin()

        // 2. Initialize and set the version-specific adapter
        this.versionAdapter = ModernAdapter()

        // 3. Register commands and listeners from the common module
        CommandManager(this).registerCommands()
        server.pluginManager.registerEvents(PlayerListener(this), this)

        // 4. Register the API for other plugins to use
        server.servicesManager.register(Api::class.java, this, this, ServicePriority.Normal)

        logger.info("Modern module enabled.")
    }

    override fun onDisable() {
        unloadPlugin()
        logger.info("Modern module disabled.")
    }

    /**
     * Provides the plugin's name from the API.
     */
    override fun getPluginName(): String {
        return description.name
    }
}
