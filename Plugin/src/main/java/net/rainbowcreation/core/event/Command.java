package net.rainbowcreation.core.event;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class Command implements Listener {
    @EventHandler
    public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
        String command = event.getMessage();
        Player player = event.getPlayer();

        // Check if the command starts with "/"
        if (command.startsWith("/")) { // && !player.isOp()) {
            // Cancel the command event
            event.setCancelled(true);

            // Optionally, notify the player
            player.sendMessage("Commands are hidden.");
        }
    }
}
