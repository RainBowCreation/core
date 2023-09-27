package net.rainbowcreation.core;

import com.earth2me.essentials.Essentials;
import net.rainbowcreation.core.chat.Console;
import net.rainbowcreation.core.datamanager.Config;
import net.rainbowcreation.core.datamanager.Service;
import net.rainbowcreation.core.eventmanager.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class core extends JavaPlugin {

    private static core instance;
    public static core getInstance(){
        return instance;
    }
    public Config guiData;
    public Config playerData;
    public Essentials ess;

    @Override
    public void onEnable() {
        instance = this;
        setupConfig();
        Manager.register(Bukkit.getPluginManager(), instance);
        if (instance.getConfig().contains("Header")) {
            for (Object txt : instance.getConfig().getList("Header")) {
                Console.info(txt.toString());
            }
        }
        ess = (Essentials) instance.getServer().getPluginManager().getPlugin("Essentials");
        if (ess == null) {
            Console.info("Please install Essentials"); //checker
        }
        Service.initialize();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Bukkit.getServer().getScheduler().cancelTasks(instance);
    }

    private void setupConfig() {
        saveDefaultConfig();
        guiData = new Config(instance, "gui.yml");
        guiData.saveConfig();
        guiData.reloadConfig();
        playerData = new Config(instance, "userdata.yml");
        playerData.saveConfig();
        playerData.reloadConfig();
    }
}
