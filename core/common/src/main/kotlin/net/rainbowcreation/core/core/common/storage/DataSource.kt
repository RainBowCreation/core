package net.rainbowcreation.core.core.common.storage

import org.bukkit.entity.Player
import java.util.UUID

/**
 * An interface that defines the contract for all data storage operations.
 *
 * This allows the plugin to easily switch between different data sources
 * (e.g., MySQL, SQLite, YAML files) by creating a new class that
 * implements this interface.
 */
interface DataSource {
    /**
     * Initializes the connection to the data source.
     */
    fun connect()

    /**
     * Disconnects from the data source and cleans up resources.
     */
    fun disconnect()

    /**
     * Loads a player's data from the data source.
     *
     * @param uuid The unique ID of the player to load.
     * @return A data class containing the player's information, or null if not found.
     */
    fun getPlayerData(uuid: UUID): Any? // Replace 'Any' with your specific PlayerData class

    /**
     * Saves a player's data to the data source.
     *
     * @param player The player whose data is to be saved.
     * @param playerData The data object to save.
     */
    fun savePlayerData(player: Player, playerData: Any) // Replace 'Any' with your specific PlayerData class
}
