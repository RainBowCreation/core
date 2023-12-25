package net.rainbowcreation.core.event;

import net.rainbowcreation.core.Core;
import net.rainbowcreation.core.api.utils.Gui;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class Interact implements Listener {
    private static Core pS_core;
    public Interact(Core plugin) {
        pS_core = plugin;
    }
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        pS_core.P_console.info(event.getAction().name());
        // Check if the player pressed the swap hands key (default is F)
        if (event.getAction().name().contains("SWAP_HELD_ITEMS")) {
            // Custom logic for hand swap
            Bukkit.broadcastMessage(player.getName() + " swapped hands!");

            // You can add your own logic here based on the hand swap
        }
        if (player.isSneaking()) {
            if (event.getAction().name().contains("LEFT_CLICK_AIR")) {
                player.openInventory(new Gui(2).get());
            }
        }
    }
}
