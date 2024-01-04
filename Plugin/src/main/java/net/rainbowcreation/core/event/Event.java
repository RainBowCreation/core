package net.rainbowcreation.core.event;

import net.rainbowcreation.core.Core;
import net.rainbowcreation.core.api.ICore;
import net.rainbowcreation.core.api.IRegistry;
import net.rainbowcreation.core.api.IEvent;
import net.rainbowcreation.core.api.utils.Remap;
import net.rainbowcreation.core.event.player.Join;
import org.bukkit.plugin.PluginManager;

public class Event implements IRegistry {

    @Override
    public void register() {
        final PluginManager manager = Core.getInstance().getServer().getPluginManager();
        final ICore core = Core.getInstance();

        // global lobby event
        if (Core.getInstance().isLobby())
            manager.registerEvents(new Join(), core.getPlugin());

        // per version event
        final IEvent event = (IEvent) Remap.castInterface(IEvent.class, core.getVersion(), "event.Event");
        if (event == null)
            return;
        event.register(manager, core.getPlugin());
    }

    @Override
    public Object get(String key) {
        return null;
    }
}
