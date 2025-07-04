package net.rainbowcreation.core.event.player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class PickupItem implements Listener {
    @EventHandler
    public void onPlayerPickupItem(PlayerPickupItemEvent event) {
        ItemStack item = event.getItem().getItemStack();
        UUID uuid = event.getItem().getUniqueId();
        event.getPlayer().sendMessage("You picked up: " + item.getType() + " with UUID: " + uuid);
    }
}
