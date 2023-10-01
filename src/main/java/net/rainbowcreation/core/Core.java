package net.rainbowcreation.core;

import com.earth2me.essentials.Essentials;
import net.rainbowcreation.core.chat.Console;
import net.rainbowcreation.core.datamanager.Config;
import net.rainbowcreation.core.datamanager.Service;
import net.rainbowcreation.core.eventmanager.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.List;

public final class Core extends JavaPlugin {
    private static Core instance;
    public static Core getInstance(){
        return instance;
    }
    public Config guiData;
    public Config playerData;
    public Essentials ess;

    @Override
    public void onEnable() {
        instance = this;
        List<String> header = Arrays.asList("#####################################################################################",
                                            "#  _____       _       ____                 _____                _   _              #",
                                            "# |  __ \\     (_)     |  _ \\               / ____|              | | (_)             #",
                                            "# | |__) |__ _ _ _ __ | |_) | _____      _| |     _ __ ___  __ _| |_ _  ___  _  __   #",
                                            "# |  _  // _` | | '_ \\|  _ < / _ \\ \\ /\\ / / |    | '__/ _ \\/ _`  | __| |/ _ \\| '_ \\  #",
                                            "# | | \\ \\ (_| | | | | | |_) | (_) \\ V  V /| |____|  | |  __/ (_| | |_| | (_) | | | | #",
                                            "# |_|  \\_\\__,_|_|_| |_|____/ \\___/ \\_/\\_/  \\_____|_|  \\___|\\__,_|\\__|_|\\___/|_|  |_| #",
                                            "#                                                                                   #",
                                            "###########################################################################core######");
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
