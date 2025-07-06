package net.rainbowcreation.core.core.common.config

import org.bukkit.configuration.file.FileConfiguration

/**
 * A class to hold and manage all plugin settings loaded from a config.yml.
 */
class Settings(config: FileConfiguration) {

    // Database settings are loaded from the 'database' section of the config.
    val dbHost: String = config.getString("database.host", "localhost")!!
    val dbPort: Int = config.getInt("database.port", 3306)
    val dbName: String = config.getString("database.name", "database")!!
    val dbUser: String = config.getString("database.user", "user")!!
    val dbPass: String = config.getString("database.password", "password")!!
}