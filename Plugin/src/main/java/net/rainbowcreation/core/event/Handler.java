package net.rainbowcreation.core.event;

import net.rainbowcreation.core.Core;
import net.rainbowcreation.core.api.event.IEventHandler;
import net.rainbowcreation.core.api.utils.Remap;
import org.bukkit.plugin.PluginManager;

public class Handler {
    private static Core pS_core;
    public static void register(PluginManager manager, Core instance) {
        pS_core = instance;
        // manager.registerEvents(new Command(), p_instance);
        // manager.registerEvents(new Interact(), p_instance);
        manager.registerEvents(new InvClick(pS_core), pS_core);
        // manager.registerEvents(new Craft(), p_instance);
        manager.registerEvents(new PlayerMove(pS_core), pS_core);
        manager.registerEvents(new PlayerJoin(pS_core), pS_core);
        final IEventHandler F_handler = (IEventHandler) Remap.castInterface(IEventHandler.class, pS_core.P_version, "event.Handler");
        if (F_handler == null)
            return;
        F_handler.register(manager, pS_core);
    }
}
