package net.rainbowcreation.rainbowcreationx.datamanager;

import net.rainbowcreation.rainbowcreationx.RainBowCreationX;
import net.rainbowcreation.rainbowcreationx.chat.Console;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Config {
    private final RainBowCreationX plugin;
    private FileConfiguration dataConfig = null;
    private File configFile = null;
    private final String file;

    public Config(RainBowCreationX plugin, String file) {
        this.plugin = plugin;
        this.file = file;
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
        Console.info("Trying to save");
        try {
            if (this.dataConfig == null || this.configFile == null) {
                Console.info("Error something null");
                return;
            }
            getConfig().save(this.configFile);
            Console.info("Saved");
        } catch (Exception e) {Console.info(e.toString());}
    }

    public void saveDefaultConfig() {
        if (this.configFile == null)
            this.configFile = new File(this.plugin.getDataFolder(), this.file);
        if (!this.configFile.exists())
            this.plugin.saveResource(this.file, false);
    }
}
