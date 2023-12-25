package net.rainbowcreation.core.event;

import net.rainbowcreation.core.Core;
import net.rainbowcreation.core.gui.Warp;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerMove implements Listener {
    private static Core pS_core;
    private World world; // Set this to the world where your portal is located
    private double portalMinX = 3.5, portalMaxX = 5.5; // Set these to the X-axis bounds of your portal
    private double portalMinZ = -1.5, portalMaxZ = 2.5; // Set these to the Z-axis bounds of your portal
    private double portalMinY = 42.5, portalMaxY = 53.5;

    public PlayerMove(Core plugin) {
        pS_core = plugin;
        world = plugin.getServer().getWorld("world");
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        final Player F_player = event.getPlayer();
        final Location F_playerLocation = F_player.getLocation();

        if (isPlayerNearPortal(F_playerLocation)) {
            // Player is near the portal
            pS_core.P_console.info(pS_core.P_playerlog.get(event.getPlayer()).toString());
            if (pS_core.P_playerlog.get(F_player)) {
                F_player.sendMessage("You are near the portal!");
                // do the warp thing


                pS_core.P_playerlog.put(F_player, false);
                // F_player.openInventory(Warp.S_get());
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        pS_core.P_playerlog.put(F_player, true);
                    }
                }.runTaskLater(pS_core, 100L);
            }
        }
    }

    private boolean isPlayerNearPortal(Location playerLocation) {
        // 3.5 42.5 -1.5
        // 5.5 50.5 2.5
        if (world == null)
            return false;
        if (playerLocation.getWorld() != world)
            return false;
        if (playerLocation.getX() < portalMinX || playerLocation.getX() > portalMaxX)
            return false;
        if (playerLocation.getZ() < portalMinZ || playerLocation.getZ() > portalMaxZ)
            return false;
        if (playerLocation.getY() < portalMinY || playerLocation.getY() > portalMaxY)
            return false;
        return true;
    }
}
