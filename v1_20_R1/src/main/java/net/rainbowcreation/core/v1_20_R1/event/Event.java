package net.rainbowcreation.core.v1_20_R1.event;

import net.rainbowcreation.core.api.IEvent;
import net.rainbowcreation.core.v1_20_R1.Core;
import net.rainbowcreation.core.v1_20_R1.event.inventory.Click;
import net.rainbowcreation.core.v1_20_R1.event.player.Interact;
import net.rainbowcreation.core.v1_20_R1.event.player.Move;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

@SuppressWarnings("unused")
public class Event implements IEvent {
    @Override
    public void register(PluginManager manager, Plugin instance) {
        manager.registerEvents(new Click(), instance);
        if (Core.instance.getDefaultConfig().getString("bungeecord.this").equals("lobby"))
            manager.registerEvents(new Move(), instance);
        manager.registerEvents(new Interact(), instance);
    }
}
