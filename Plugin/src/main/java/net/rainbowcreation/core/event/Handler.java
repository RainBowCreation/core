package net.rainbowcreation.core.event;

import net.rainbowcreation.core.Core;
import net.rainbowcreation.core.api.event.IEventHandler;
import net.rainbowcreation.core.api.event.IPlayerJoinEvent;
import net.rainbowcreation.core.api.utils.Remap;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

public class Handler implements IEventHandler {
    private final Core pF_instance = Core.PS_getInstance();
    @Override
    public void register(PluginManager manager, Plugin instance) {
        final Listener F_clazz = (Listener) Remap.castInterface(IPlayerJoinEvent.class, pF_instance.P_version, "event.PlayerJoinEvent");
        if (F_clazz == null) {
            return;
        }
        manager.registerEvents(F_clazz, instance);
    }
}
