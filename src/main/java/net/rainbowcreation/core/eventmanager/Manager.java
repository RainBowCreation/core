package net.rainbowcreation.core.eventmanager;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

public class Manager {
    public static void register(PluginManager manager, Plugin instance) {
        manager.registerEvents(new onPlayerSwapHandItem(), instance);
        manager.registerEvents(new GuiClick(), instance);
        manager.registerEvents(new GuiClose(), instance);
        manager.registerEvents(new onPlayerJoin(), instance);
        manager.registerEvents(new GuiDrag(), instance);
        manager.registerEvents(new onChat(), instance);
    }
}
