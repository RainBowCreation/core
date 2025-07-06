package net.rainbowcreation.core.core.common.version

import org.bukkit.entity.Player

/**
 * An interface that defines a contract for version-specific functionality.
 *
 * This allows the main plugin logic to remain agnostic of the server version,
 * calling these methods without needing to know the underlying implementation.
 */
interface VersionAdapter {
    /**
     * Sends a message to the player's action bar.
     *
     * @param player The player to send the message to.
     * @param message The text message to send.
     */
    fun sendActionbar(
        player: Player,
        message: String,
    )

    /**
     * Checks if the server is running on the Folia platform.
     *
     * @return `true` if the server is Folia, `false` otherwise.
     */
    fun isFolia(): Boolean

    /**
     * Checks if the server is running on the Multipaper platform.
     *
     * @return `true` if the server is Multipaper, `false` otherwise.
     */
    fun isMultipaper(): Boolean
}
