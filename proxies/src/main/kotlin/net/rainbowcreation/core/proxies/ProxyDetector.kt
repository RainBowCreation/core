package net.rainbowcreation.core.proxies

/**
 * A utility to detect the current proxy server environment.
 */
object ProxyDetector {
    /**
     * An enum representing the possible proxy server types.
     */
    enum class ProxyServerType {
        BUNGEECORD,
        VELOCITY,
        UNKNOWN,
    }

    /**
     * Detects the proxy server type by checking for the existence of unique classes.
     *
     * @return The detected [ProxyServerType].
     */
    fun detectProxy(): ProxyServerType {
        if (hasClass("com.velocitypowered.api.proxy.ProxyServer")) {
            return ProxyServerType.VELOCITY
        }
        if (hasClass("net.md_5.bungee.api.ProxyServer")) {
            return ProxyServerType.BUNGEECORD
        }
        return ProxyServerType.UNKNOWN
    }

    /**
     * Safely checks if a class exists on the classpath.
     *
     * @param className The fully qualified name of the class to check.
     * @return `true` if the class is found, `false` otherwise.
     */
    private fun hasClass(className: String): Boolean {
        return try {
            Class.forName(className)
            true
        } catch (e: ClassNotFoundException) {
            false
        }
    }
}
