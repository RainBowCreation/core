package net.rainbowcreation.core.core.legacy

import io.github.invvk.actionbar.api.ActionBarAPI.send
import net.rainbowcreation.core.core.common.version.VersionAdapter
import org.bukkit.entity.Player

/**
 * Version adapter for legacy Minecraft servers (1.8 - 1.16.5).
 *
 * This implementation uses the ActionBarApi library to send action bar
 * messages, avoiding the need for complex and unsafe reflection.
 */
class LegacyAdapter : VersionAdapter {

    /**
     * Sends an action bar message using the ActionBarApi library.
     */
    override fun sendActionbar(player: Player, message: String) {
        send(player, message)
    }

    /**
     * Folia does not exist on these legacy versions, so this always returns false.
     */
    override fun isFolia(): Boolean {
        return false
    }

    /**
     * Multipaper does not exist on these legacy versions, so this always returns false.
     */
    override fun isMultipaper(): Boolean {
        return false
    }
}