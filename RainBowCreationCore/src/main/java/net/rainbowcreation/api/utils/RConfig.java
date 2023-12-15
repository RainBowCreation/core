package net.rainbowcreation.api.utils;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Filconfig manager
 */
public class RConfig {
    private final Plugin plugin;
    private FileConfiguration dataConfig = null;
    private File configFile = null;
    private final String file;

    private RConsole console;

    /**
     *
     * @param plugin
     * @param file
     * @param console
     */
    public RConfig(Plugin plugin, String file, RConsole console) {
        this.plugin = plugin;
        this.file = file;
        this.console = console;
        saveDefaultConfig();
    }

    /**
     * fetch config
     */
    public void reloadConfig() {
        if (this.configFile == null)
            this.configFile = new File(this.plugin.getDataFolder(), this.file);
        this.dataConfig = YamlConfiguration.loadConfiguration(this.configFile);
        final InputStream defaultStream = this.plugin.getResource(this.file);
        if (defaultStream != null) {
            final YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultStream));
            this.dataConfig.setDefaults(defaultConfig);
        }
    }

    /**
     *
     * @return config
     */
    public FileConfiguration getConfig() {
        if (this.dataConfig == null)
            reloadConfig();
        return this.dataConfig;
    }

    /**
     * save
     */
    public void saveConfig() {
        console.info("Trying to save");
        try {
            if (this.dataConfig == null || this.configFile == null) {
                console.info("Error something null");
                return;
            }
            getConfig().save(this.configFile);
            console.info("Saved");
        } catch (Exception e) {
            console.info(e.toString());
        }
    }

    /**
     * save
     */
    public void saveDefaultConfig() {
        if (this.configFile == null)
            this.configFile = new File(this.plugin.getDataFolder(), this.file);
        if (!this.configFile.exists())
            this.plugin.saveResource(this.file, false);
    }
}
