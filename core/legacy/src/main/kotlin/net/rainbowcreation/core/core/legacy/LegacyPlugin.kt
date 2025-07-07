package net.rainbowcreation.core.core.legacy

import net.rainbowcreation.core.api.Api
import net.rainbowcreation.core.core.common.AbstractPlugin
import net.rainbowcreation.core.core.common.commands.CommandManager
import net.rainbowcreation.core.core.common.events.PlayerListener
import org.bukkit.plugin.ServicePriority

/**
 * The main plugin class for legacy servers (1.8 - 1.16.5).
 */
class LegacyPlugin : AbstractPlugin(), Api {
    override fun onEnable() {
        // 1. Load common components (config, datasource)
        loadPlugin()

        // 2. Initialize and set the version-specific adapter
        this.versionAdapter = LegacyAdapter()

        // 3. Register commands and listeners from the common module
        CommandManager(this).registerCommands()
        server.pluginManager.registerEvents(PlayerListener(this), this)

        // 4. Register the API for other plugins to use
        server.servicesManager.register(Api::class.java, this, this, ServicePriority.Normal)
        dataSource.appendTestResult(
            description.version + "-legacy",
            this.versionAdapter.isFolia(),
            this.versionAdapter.isMultipaper(),
            commonStatus = false,
            finalStatus = true,
        )
        logger.info("Legacy module enabled.")
    }

    override fun onDisable() {
        unloadPlugin()
        logger.info("Legacy module disabled.")
    }

    /**
     * Provides the plugin's name from the API.
     */
    override fun getPluginName(): String {
        return description.name
    }
}
