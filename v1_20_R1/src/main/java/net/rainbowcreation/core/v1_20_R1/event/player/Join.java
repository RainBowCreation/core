package net.rainbowcreation.core.v1_20_R1.event.player;

import net.rainbowcreation.core.v1_20_R1.Core;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class Join implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (Core.instance.isLobby()) {
            if (event.getPlayer().hasPermission("rbc.glow"))
                event.getPlayer().setGlowing(true);
        }
    }
}
