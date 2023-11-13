package net.rainbowcreation.core;

import com.earth2me.essentials.Essentials;
import net.rainbowcreation.core.chat.console.Console;
import net.rainbowcreation.core.datamanager.config.Config;
import net.rainbowcreation.core.datamanager.service.Service;
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
    public String version = "N/A";

    @Override
    public void onEnable() {
        instance = this;
        if (!setupManager()) {
            Console.info("Fail to setup plugin! Running on incompatible server versions");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
        List<String> header = Arrays.asList(version+ "##############################################################################",
                                            "#  _____       _       ____                 _____                _   _               #",
                                            "# |  __ \\     (_)     |  _ \\               / ____|              | | (_)              #",
                                            "# | |__) |__ _ _ _ __ | |_) | _____      _| |     _ __ ___  __ _| |_ _  ___  _  __   #",
                                            "# |  _  // _` | | '_ \\|  _ < / _ \\ \\ /\\ / / |    | '__/ _ \\/ _`  | __| |/ _ \\| '_ \\  #",
                                            "# | | \\ \\ (_| | | | | | |_) | (_) \\ V  V /| |____|  | |  __/ (_| | |_| | (_) | | | | #",
                                            "# |_|  \\_\\__,_|_|_| |_|____/ \\___/ \\_/\\_/  \\_____|_|  \\___|\\__,_|\\__|_|\\___/|_|  |_| #",
                                            "#                                                                                    #",
                                            "###########################################################################core#######");
        for (String txt : header) {
            Console.info(txt);
        }
        setupConfig();
        EventManager.register(Bukkit.getPluginManager(), instance);
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
        // save and disable data
    }

    private void setupConfig() {
        saveDefaultConfig();
        guiData = new Config(instance, "gui.yml");
        guiData.saveConfig();
        playerData = new Config(instance, "userdata.yml");
        playerData.saveConfig();
        playerData.reloadConfig();
    }

    private boolean setupManager() {
        try {
            version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
        Console.info("Version ->" + version);
        switch (version.substring(0,version.indexOf("R"))) {
            //fill case of any version
            case ("v1_12_"):
                break;
            case ("v1_19_"):
                break;
        };
        return true;
    }
}
