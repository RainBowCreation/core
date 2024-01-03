package net.rainbowcreation.core.v1_8_R3.event.player;

import net.rainbowcreation.core.v1_8_R3.Core;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class Join implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (Core.instance.isLobby()) {
            // is lobby do glow effect
        }
    }
}
