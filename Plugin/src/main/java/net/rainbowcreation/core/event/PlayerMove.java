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
    private World world = Core.PS_getInstance().getServer().getWorld("world"); // Set this to the world where your portal is located
    private double portalMinX = 3.5, portalMaxX = 5.5; // Set these to the X-axis bounds of your portal
    private double portalMinZ = -1.5, portalMaxZ = 2.5; // Set these to the Z-axis bounds of your portal
    private double portalMinY = 42.5, portalMaxY = 53.5;
    private Core p_core;
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        final Player F_player = event.getPlayer();
        final Location F_playerLocation = F_player.getLocation();

        if (isPlayerNearPortal(F_playerLocation)) {
            // Player is near the portal
            F_player.sendMessage("You are near the portal!");
            if (Core.PS_getInstance().P_playerlog.get(F_player)) {
                Core.PS_getInstance().P_playerlog.replace(F_player, false);
                F_player.openInventory(Warp.S_get());
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        Core.PS_getInstance().P_playerlog.replace(F_player, true);
                    }
                }.runTaskLater(Core.PS_getInstance(), 100L);
            }
        }
    }

    private boolean isPlayerNearPortal(Location playerLocation) {
        // 3.5 42.5 -1.5
        // 5.5 50.5 2.5
        if (world != null) {
            return playerLocation.getWorld() == world &&
                    playerLocation.getX() >= portalMinX && playerLocation.getX() <= portalMaxX &&
                    playerLocation.getZ() >= portalMinZ && playerLocation.getZ() <= portalMaxZ &&
                    playerLocation.getY() >= portalMinY && playerLocation.getY() <= portalMaxY;
        }
        return false;
    }
}
