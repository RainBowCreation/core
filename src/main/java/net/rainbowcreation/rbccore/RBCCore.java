package net.rainbowcreation.rbccore;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class RBCCore extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.getLogger().info("HelloWorld");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
