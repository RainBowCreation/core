package net.rainbowcreation.core.v1_8_R3.event;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerJoinEvent implements Listener {
    @EventHandler
    public void onJoin(org.bukkit.event.player.PlayerJoinEvent event) {
        event.getPlayer().sendMessage("Hi");
    }

    public PlayerJoinEvent newInstance() {
        return this;
    }
}
