package net.rainbowcreation.core.event.player;

import net.rainbowcreation.core.Core;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class Join implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Core.getInstance().playerlog.put(event.getPlayer(), true);
    }
}
