package net.rainbowcreation.core.api.event;

import org.bukkit.event.Listener;

public interface IPlayerJoinEvent extends Listener {
    void onJoin(org.bukkit.event.player.PlayerJoinEvent event);
}
