package net.rainbowcreation.core.core.modern

import com.github.puregero.multilib.MultiLib
import net.kyori.adventure.text.Component
import net.rainbowcreation.core.core.common.version.VersionAdapter
import org.bukkit.entity.Player

/**
 * Version adapter for modern Minecraft servers (1.17+).
 */
class ModernAdapter : VersionAdapter {
    /**
     * A private, lazy-initialized property to perform the Folia check only once.
     * The name is changed to avoid any conflicts.
     */
    private val isFoliaServer: Boolean by lazy {
        try {
            Class.forName("io.papermc.paper.threadedregions.RegionizedServer")
            true
        } catch (e: ClassNotFoundException) {
            false
        }
    }

    /**
     * Sends an action bar message using the modern Adventure components API.
     */
    override fun sendActionbar(
        player: Player,
        message: String,
    ) {
        player.sendActionBar(Component.text(message))
    }

    /**
     * Explicitly overrides the function from the interface and returns the cached result.
     * This removes the compiler confusion.
     */
    override fun isFolia(): Boolean {
        return isFoliaServer
    }

    override fun isMultipaper(): Boolean {
        return MultiLib.isMultiPaper()
    }
}
