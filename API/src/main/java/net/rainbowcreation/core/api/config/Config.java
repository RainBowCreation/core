package net.rainbowcreation.core.api.config;

import net.rainbowcreation.core.api.utils.Console;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Config {
    private final Plugin plugin;
    private FileConfiguration dataConfig = null;
    private File configFile = null;
    private final String file;
    private Console console;

    public Config(Plugin plugin, String file, Console console) {
        this.plugin = plugin;
        this.file = file;
        this.console = console;
        saveDefaultConfig();
    }

    public void reloadConfig() {
        if (this.configFile == null)
            this.configFile = new File(this.plugin.getDataFolder(), this.file);
        this.dataConfig = YamlConfiguration.loadConfiguration(this.configFile);
        InputStream defaultStream = this.plugin.getResource(this.file);
        if (defaultStream != null) {
            YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultStream));
            this.dataConfig.setDefaults(defaultConfig);
        }
    }

    public FileConfiguration getConfig() {
        if (this.dataConfig == null)
            reloadConfig();
        return this.dataConfig;
    }

    public void saveConfig() {
        console.info("Trying to save");
        try {
            if (this.dataConfig == null || this.configFile == null) {
                console.info("Error something null");
                return;
            }
            getConfig().save(this.configFile);
            console.info("Saved");
        } catch (Exception e) {console.info(e.toString());}
    }

    public void saveDefaultConfig() {
        if (this.configFile == null)
            this.configFile = new File(this.plugin.getDataFolder(), this.file);
        if (!this.configFile.exists())
            this.plugin.saveResource(this.file, false);
    }
}