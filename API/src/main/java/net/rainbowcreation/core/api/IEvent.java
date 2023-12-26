package net.rainbowcreation.core.api;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

public interface IEvent {
    void register(PluginManager manager, Plugin plugin);
}
