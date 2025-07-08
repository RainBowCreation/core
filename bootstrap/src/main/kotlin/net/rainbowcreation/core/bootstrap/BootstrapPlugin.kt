package net.rainbowcreation.core.bootstrap

import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import java.io.*
import java.util.jar.*

class BootstrapPlugin : JavaPlugin() {

    override fun onLoad() {
        try {
            val version = detectVersion()
            logger.info("Detected version: $version")

            val pluginYmlName = when (version) {
                "modern" -> "plugin-modern.yml"
                "legacy" -> "plugin-legacy.yml"
                else -> throw IllegalStateException("Unknown version: $version")
            }

            val patchedPlugin = extractEmbeddedPlugin(version, pluginYmlName)
            loadRealPlugin(patchedPlugin)

            logger.info("Disabling bootstrap plugin.")
            Bukkit.getPluginManager().disablePlugin(this)
        } catch (e: Exception) {
            logger.severe("Failed to bootstrap plugin: ${e.message}")
            e.printStackTrace()
        }
    }

    private fun detectVersion(): String {
        val version = Bukkit.getBukkitVersion()
        logger.info("Checking Bukkit version: $version")

        val numeric = version.substringBefore('-')
        val parts = numeric.split('.')
        val minor = parts.getOrNull(1)?.toIntOrNull() ?: return "legacy"

        logger.info("Detected Bukkit minor version: $minor")
        return if (minor >= 17) "modern" else "legacy"
    }

    private fun extractEmbeddedPlugin(version: String, pluginYmlName: String): File {
        val sourceJar = File(javaClass.protectionDomain.codeSource.location.toURI())
        val outputDir = sourceJar.parentFile
        val outputJar = File(outputDir, "patched-plugin.jar")

        logger.info("Extracting embedded $version plugin to $outputJar")

        JarInputStream(FileInputStream(sourceJar)).use { inputJar ->
            var embeddedJarEntry: JarEntry? = null
            val embeddedPrefix = "net/rainbowcreation/core/modules/$version/"

            var entry: JarEntry? = inputJar.nextJarEntry
            while (entry != null) {
                if (entry.name.startsWith(embeddedPrefix) && entry.name.endsWith(".jar")) {
                    embeddedJarEntry = JarEntry(entry.name)
                    break
                }
                entry = inputJar.nextJarEntry
            }

            if (embeddedJarEntry == null) {
                throw FileNotFoundException("Could not find embedded jar under $embeddedPrefix")
            }

            // Extract the embedded jar into memory
            val embeddedJarBytes = ByteArrayOutputStream()
            inputJar.copyTo(embeddedJarBytes)

            // Inject plugin.yml into the embedded jar
            val finalJarBytes = ByteArrayOutputStream()
            JarInputStream(ByteArrayInputStream(embeddedJarBytes.toByteArray())).use { embeddedInput ->
                JarOutputStream(finalJarBytes).use { output ->
                    val buffer = ByteArray(4096)
                    var subEntry = embeddedInput.nextJarEntry
                    while (subEntry != null) {
                        if (subEntry.name != "plugin.yml") {
                            output.putNextEntry(JarEntry(subEntry.name))
                            embeddedInput.copyTo(output, buffer.size)
                            output.closeEntry()
                        }
                        subEntry = embeddedInput.nextJarEntry
                    }

                    // Inject correct plugin.yml
                    output.putNextEntry(JarEntry("plugin.yml"))
                    getResource(pluginYmlName)?.copyTo(output) ?: throw FileNotFoundException("Could not find $pluginYmlName")
                    output.closeEntry()
                }
            }

            // Write the patched jar to file
            FileOutputStream(outputJar).use { fileOut ->
                fileOut.write(finalJarBytes.toByteArray())
            }
        }

        return outputJar
    }

    private fun loadRealPlugin(jar: File) {
        val pluginManager = Bukkit.getPluginManager()
        val loadPluginMethod = pluginManager.javaClass.getMethod("loadPlugin", File::class.java)

        val realPlugin = loadPluginMethod.invoke(pluginManager, jar) as JavaPlugin
        pluginManager.enablePlugin(realPlugin)
    }
}
