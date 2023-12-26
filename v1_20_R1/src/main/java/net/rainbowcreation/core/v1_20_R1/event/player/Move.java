package net.rainbowcreation.core.v1_20_R1.event.player;

import com.github.puregero.multilib.MultiLib;
import net.rainbowcreation.core.api.ICore;
import net.rainbowcreation.core.v1_20_R1.Core;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class Move implements Listener {
    private static ICore core;
    private World world; // Set this to the world where your portal is located
    private double portal_min_x = 3.5, portal_max_x = 5.5; // Set these to the X-axis bounds of your portal
    private double portal_min_z = -1.5, portal_max_z = 2.5; // Set these to the Z-axis bounds of your portal
    private double portal_min_y = 42.5, portal_max_y = 53.5;

    public Move() {
        core = Core.instance;
        world = core.getPlugin().getServer().getWorld("world");
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        final Player player = event.getPlayer();
        if (MultiLib.isMultiPaper()) {
            if (!MultiLib.isLocalPlayer(player))
                return;
        }
        final Location player_location = player.getLocation();

        if (isPlayerNearPortal(player_location)) {
            // Player is near the portal
            core.getConsole().info(core.getPlayerLog().get(event.getPlayer()).toString());
            if (core.getPlayerLog().get(player)) {
                player.sendMessage("You are near the portal!");
                // do the warp thing


                core.getPlayerLog().put(player, false);
                // F_player.openInventory(Warp.S_get());
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        core.getPlayerLog().put(player, true);
                    }
                }.runTaskLater(core.getPlugin(), 100L);
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
