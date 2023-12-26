package net.rainbowcreation.core.v1_20_R1.event;

import net.rainbowcreation.core.api.IEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

@SuppressWarnings("unused")
public class Event implements IEvent {
    @Override
    public void register(PluginManager manager, Plugin instance) {
        // manager.registerEvents(new Click(), instance);
    }
}
