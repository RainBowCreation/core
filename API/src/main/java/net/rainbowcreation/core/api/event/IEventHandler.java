package net.rainbowcreation.core.api.event;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

public interface IEventHandler {
    void register(PluginManager manager, Plugin plugin);
}
