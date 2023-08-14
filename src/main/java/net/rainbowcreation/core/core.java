package net.rainbowcreation.core;

import com.earth2me.essentials.Essentials;
import net.rainbowcreation.core.chat.Console;
import net.rainbowcreation.core.datamanager.Config;
import net.rainbowcreation.core.eventmanager.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
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
        Console.info("Server Started!!");
        setupConfig();
        PluginManager manager = Bukkit.getPluginManager();
        manager.registerEvents(new onPlayerSwapHandItem(), instance);
        manager.registerEvents(new GuiClick(), instance);
        manager.registerEvents(new GuiClose(), instance);
        manager.registerEvents(new onPlayerJoin(), instance);
        manager.registerEvents(new GuiDrag(), instance);
        manager.registerEvents(new onChat(), instance);
        ess = (Essentials) instance.getServer().getPluginManager().getPlugin("Essentials");
        if (ess == null) {
            Console.info("Please install Essentials");
        }
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
