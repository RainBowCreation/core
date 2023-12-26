package net.rainbowcreation.core.event.player;

import net.rainbowcreation.core.Core;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class Move implements Listener {
    private static Core core = Core.getInstance();
    private World world = core.getServer().getWorld("world");; // Set this to the world where your portal is located
    private double portal_min_x = 3.5, portal_max_x = 5.5; // Set these to the X-axis bounds of your portal
    private double portal_min_z = -1.5, portal_max_z = 2.5; // Set these to the Z-axis bounds of your portal
    private double portal_min_y = 42.5, portal_max_y = 53.5;

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        final Player player = event.getPlayer();
        final Location player_location = player.getLocation();

        if (isPlayerNearPortal(player_location)) {
            // Player is near the portal
            core.console.info(core.playerlog.get(event.getPlayer()).toString());
            if (core.playerlog.get(player)) {
                player.sendMessage("You are near the portal!");
                // do the warp thing


                core.playerlog.put(player, false);
                // F_player.openInventory(Warp.S_get());
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        core.playerlog.put(player, true);
                    }
                }.runTaskLater(core, 100L);
            }
        }
    }

    private boolean isPlayerNearPortal(Location player_location) {
        // 3.5 42.5 -1.5
        // 5.5 50.5 2.5
        if (world == null)
            return false;
        if (player_location.getWorld() != world)
            return false;
        if (player_location.getX() < portal_min_x || player_location.getX() > portal_max_x)
            return false;
        if (player_location.getZ() < portal_min_z || player_location.getZ() > portal_max_z)
            return false;
        if (player_location.getY() < portal_min_y || player_location.getY() > portal_max_y)
            return false;
        return true;
    }
}
