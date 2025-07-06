package net.rainbowcreation.core.bootstrap

import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.util.jar.JarEntry
import java.util.jar.JarInputStream
import java.util.jar.JarOutputStream

class BootstrapPlugin : JavaPlugin() {
    override fun onLoad() {
        try {
            val version = detectVersion()
            logger.info("Detected version: $version")

            val pluginYmlName =
                when (version) {
                    "modern" -> "plugin-modern.yml"
                    "legacy" -> "plugin-legacy.yml"
                    else -> throw IllegalStateException("Unknown version type: $version")
                }

            val newJar = patchPluginYml(pluginYmlName)
            loadRealPlugin(newJar)

            // disable the bootstrap plugin after the main plugin loaded
            logger.info("Disabling bootstrap plugin.")
            Bukkit.getPluginManager().disablePlugin(this)
        } catch (ex: Exception) {
            logger.severe("Failed to bootstrap plugin: ${ex.message}")
            ex.printStackTrace()
        }
    }

    private fun detectVersion(): String {
        val version = Bukkit.getBukkitVersion()
        logger.info("Checking Bukkit version: $version")

        val numericVersion = version.substringBefore('-')
        val parts = numericVersion.split('.')

        if (parts.size >= 2) {
            val minor = parts[1].toIntOrNull() ?: return "legacy"
            logger.info("Detected Bukkit minor version: $minor")
            return if (minor >= 17) "modern" else "legacy"
        }

        return "legacy"
    }

    private fun patchPluginYml(pluginYmlName: String): File {
        val sourceJar = File(javaClass.protectionDomain.codeSource.location.toURI())
        val sharedFolder: File = File(dataFolder.getParentFile(), description.name.replace("-bootstrap", ""))
        val tempJar = File(sharedFolder, "patched-plugin.jar")

        if (!sharedFolder.exists()) sharedFolder.mkdirs()

        JarInputStream(FileInputStream(sourceJar)).use { jis ->
            JarOutputStream(FileOutputStream(tempJar)).use { jos ->
                val buffer = ByteArray(4096)

                var entry = jis.nextJarEntry
                while (entry != null) {
                    if (entry.name == "plugin.yml") {
                        entry = jis.nextJarEntry
                        continue
                    }

                    jos.putNextEntry(JarEntry(entry.name))
                    jis.copyTo(jos, buffer.size)
                    jos.closeEntry()
                    entry = jis.nextJarEntry
                }

                jos.putNextEntry(JarEntry("plugin.yml"))
                val pluginYmlStream =
                    getResource(pluginYmlName)
                        ?: throw FileNotFoundException("Could not find resource: $pluginYmlName")
                pluginYmlStream.copyTo(jos, buffer.size)
                jos.closeEntry()
            }
        }

        return tempJar
    }

    private fun loadRealPlugin(jar: File) {
        val pluginManager = Bukkit.getPluginManager()
        val loadPluginMethod = pluginManager.javaClass.getMethod("loadPlugin", File::class.java)

        val plugin = loadPluginMethod.invoke(pluginManager, jar) as JavaPlugin
        pluginManager.enablePlugin(plugin)
    }
}
