package net.rainbowcreation.core.proxies

import net.rainbowcreation.core.proxies.bungee.BungeePlugin
import net.md_5.bungee.api.plugin.Plugin

/**
 * This class serves as the main entry point ONLY for BungeeCord, as defined
 * in the `bungee.yml`. It detects the proxy type and initializes the
 * correct plugin logic.
 *
 * Velocity uses its own annotation-based system and does not use this class.
 */
class MainProxy : Plugin() {

    private val actualPlugin: Plugin?

    init {
        // This block runs when the plugin is first loaded by the proxy.
        actualPlugin = if (ProxyDetector.detectProxy() == ProxyDetector.ProxyServerType.BUNGEECORD) {
            // If we are on BungeeCord, create an instance of our BungeePlugin logic.
            // We pass 'this' which is the proxy-injected Plugin instance.
            BungeePlugin(this)
        } else {
            // If on Velocity, we do nothing, as its own entry point will be used.
            null
        }
    }

    override fun onEnable() {
        // Delegate the onEnable call to the actual BungeeCord plugin logic.
        actualPlugin?.onEnable()
    }

    override fun onDisable() {
        // Delegate the onDisable call to the actual BungeeCord plugin logic.
        actualPlugin?.onDisable()
    }
}