package net.rainbowcreation.core.event;

import net.rainbowcreation.core.Core;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener {
    private static Core pS_core;

    public PlayerJoin(Core plugin) {
        pS_core = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        pS_core.P_playerlog.put(event.getPlayer(), true);
        pS_core.P_console.info(pS_core.P_playerlog.get(event.getPlayer()).toString());
    }
}
