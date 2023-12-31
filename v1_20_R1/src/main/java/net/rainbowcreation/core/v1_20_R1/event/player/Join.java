package net.rainbowcreation.core.v1_20_R1.event.player;

import net.rainbowcreation.core.api.utils.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class Join implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.getPlayer().getInventory().addItem(new Item().getrandomFirework());
    }
}
