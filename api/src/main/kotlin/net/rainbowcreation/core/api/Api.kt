package net.rainbowcreation.core.api

/**
 * The main interface for the MultiVersionsPluginExample API.
 *
 * Third-party plugins will use this interface to interact with your plugin
 * without needing to depend on the core implementation.
 */
interface Api {
    /**
     * Gets the name of the plugin.
     *
     * @return The plugin's name.
     */
    fun getPluginName(): String
}