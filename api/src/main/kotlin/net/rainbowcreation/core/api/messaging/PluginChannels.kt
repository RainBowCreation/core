package net.rainbowcreation.core.api.messaging

/**
 * A holder for constant plugin messaging channel names.
 */
object PluginChannels {
    /**
     * The main channel for sending data from the Bukkit server (core) to the proxy.
     * The `namespace:channel` format is the standard convention.
     */
    const val BUKKIT_TO_PROXY = "multiversionspluginexample:b2p"
}