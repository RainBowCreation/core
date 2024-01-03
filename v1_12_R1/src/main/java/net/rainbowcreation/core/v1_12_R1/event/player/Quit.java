package net.rainbowcreation.core.v1_12_R1.event.player;

import net.rainbowcreation.core.v1_12_R1.Core;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class Quit implements Listener {
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        if (Core.instance.isLobby())
            event.getPlayer().setGlowing(false);
    }
}
