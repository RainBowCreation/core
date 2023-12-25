package net.rainbowcreation.core.event;

import net.rainbowcreation.core.Core;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.Objects;

public class Command implements Listener {
    private static Core pS_core;

    public Command(Core plugin) {
        pS_core = plugin;
    }
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
        String[] command = event.getMessage().split(" ");
        Player player = event.getPlayer();

        // Check if the command starts with "/"
        if (!(command[0].equals("/login") || command[0].equals("/register") || command[0].equals("/warps")) && !player.isOp()) {
            // Cancel the command event
            event.setCancelled(true);
            // Optionally, notify the player
            player.sendMessage("RainBowCreation are zero command.");
            return;
        }
        switch (command[0]) {
            case ("/login") : {
                // do login stuffif
                if (command.length != 2)
                    player.sendMessage("/login <password>");
                break;
            }
            case ("/register") : {
                // do register stuff
                if (command.length != 3)
                    player.sendMessage("/register <password> <confirmpassword>");
                else if (!Objects.equals(command[1], command[2]))
                    player.sendMessage("password does not matched");
                break;
            }
        }
    }
}
