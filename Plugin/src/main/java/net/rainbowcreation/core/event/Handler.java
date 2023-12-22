package net.rainbowcreation.core.event;

import net.rainbowcreation.core.Core;
import net.rainbowcreation.core.api.event.IEventHandler;
import net.rainbowcreation.core.api.utils.Remap;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

public class Handler implements IEventHandler {
    private Plugin p_instance;
    private Core p_core;
    @Override
    public void register(PluginManager manager, Plugin instance) {
        p_instance = instance;
        p_core = (Core) instance;
        manager.registerEvents(new Command(), p_instance);
        manager.registerEvents(new Interact(), p_instance);
        final IEventHandler F_handler = (IEventHandler) Remap.castInterface(IEventHandler.class, p_core.P_version, "event.Handler");
        if (F_handler == null)
            return;
        F_handler.register(manager, p_instance);
    }
}
