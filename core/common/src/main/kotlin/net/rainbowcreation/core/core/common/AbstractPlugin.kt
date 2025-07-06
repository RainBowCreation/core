package net.rainbowcreation.core.core.common

import net.rainbowcreation.core.core.common.config.Settings
import net.rainbowcreation.core.core.common.storage.DataSource
import net.rainbowcreation.core.core.common.storage.MySqlDataSource
import net.rainbowcreation.core.core.common.version.VersionAdapter
import org.bukkit.plugin.java.JavaPlugin

abstract class AbstractPlugin : JavaPlugin() {

    lateinit var settings: Settings
        private set
    lateinit var dataSource: DataSource
        private set
    // Add this property to hold the version-specific implementation
    lateinit var versionAdapter: VersionAdapter
        protected set

    fun loadPlugin() {
        saveDefaultConfig()
        settings = Settings(config)
        dataSource = MySqlDataSource(settings)
        dataSource.connect()
        logger.info("Common plugin components loaded successfully.")
    }

    fun unloadPlugin() {
        dataSource.disconnect()
        logger.info("Common plugin components unloaded.")
    }
}