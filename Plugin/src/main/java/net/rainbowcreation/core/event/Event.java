package net.rainbowcreation.core.event;

import net.rainbowcreation.core.Core;
import net.rainbowcreation.core.api.IRegistry;
import net.rainbowcreation.core.api.IEvent;
import net.rainbowcreation.core.api.utils.Remap;
import net.rainbowcreation.core.event.inventory.Click;
import net.rainbowcreation.core.event.player.Join;
import net.rainbowcreation.core.event.player.Move;
import org.bukkit.plugin.PluginManager;

public class Event implements IRegistry {

    @Override
    public void register() {
        final PluginManager manager = Core.getInstance().getServer().getPluginManager();
        final Core core = Core.getInstance();

        // global event
        manager.registerEvents(new Move(), core);
        manager.registerEvents(new Join(), core);
        manager.registerEvents(new Click(), core);

        // per version event
        final IEvent event = (IEvent) Remap.castInterface(IEvent.class, core.version, "event.Event");
        if (event == null)
            return;
        event.register(manager, core);
    }

    @Override
    public Object get(String key) {
        return null;
    }
}
