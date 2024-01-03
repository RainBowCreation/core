package net.rainbowcreation.core.v1_12_R1.event.player;

import net.rainbowcreation.core.api.ICore;
import net.rainbowcreation.core.api.utils.Action;
import net.rainbowcreation.core.v1_12_R1.Core;
import net.rainbowcreation.core.v1_12_R1.gui.Main;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Move implements Listener {
    private static ICore core;
    private Map<String, Map<String, Double>> portalMap = new HashMap<>();

    public Move() {
        core = Core.instance;
        HashMap<String, Double> main_portal = new HashMap<>();
        main_portal.put("world", 0d);
        main_portal.put("min_x", 3.5);
        main_portal.put("min_y", 42.5);
        main_portal.put("min_z", -1.5);
        main_portal.put("max_x", 5.5);
        main_portal.put("max_y", 53.5);
        main_portal.put("max_z", 2.5);

        portalMap.put("mainnet", main_portal);

        HashMap<String, Double> morph_portal = new HashMap<>();
        morph_portal.put("world", 0d);
        morph_portal.put("min_x", 3.5);
        morph_portal.put("min_y", 39.5);
        morph_portal.put("min_z", 22.5);
        morph_portal.put("max_x", 6.5);
        morph_portal.put("max_y", 43.5);
        morph_portal.put("max_z", 24.5);

        portalMap.put("morph", main_portal);
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        final Player player = event.getPlayer();
        if (Main.is_move.containsKey(player)) // handle warp tp
            Main.is_move.put(player, true);

        if (!core.getPlayerLog().get(player)) // handle check cooldown
            return;

        final Location player_location = player.getLocation();

        final String server = isPlayerNearPortal(player_location);
        if (!server.equals("none")) {
            core.getBungee().sendPlayerToServer(player, server);
            core.getPlayerLog().put(player, false);
            Action.sendPlayerMessage(player, "Teleporting to "+server+" ...");
            // F_player.openInventory(Warp.S_get());
            new BukkitRunnable() {
                @Override
                public void run() {
                    core.getPlayerLog().put(player, true);
                }
            }.runTaskLater(core.getPlugin(), 100L);
        }
    }

    private String isPlayerNearPortal(Location player_location) {
        // 3.5 42.5 -1.5
        // 5.5 50.5 2.5
        final Set<String> keys = portalMap.keySet();
        for (String portal : keys) {
            String worldstr = null;
            final Map<String, Double> map = portalMap.get(portal);
            final Double v = map.get("world");
            if (v == 0d) {
                worldstr = "world";
            } else if (v == 1d) {
                worldstr = "world_nether";
            } else if (v == -1d) {
                worldstr = "world_the_end";
            }
            World world = null;
            if (worldstr != null)
                world = core.getPlugin().getServer().getWorld(worldstr);
            if (world == null)
                continue;
            if (player_location.getWorld() != world)
                continue;
            if (player_location.getY() < map.get("min_y") || player_location.getY() > map.get("max_y"))
                continue;
            if (player_location.getX() < map.get("min_x") || player_location.getX() > map.get("max_x"))
                continue;
            if (player_location.getZ() < map.get("min_z") || player_location.getZ() > map.get("max_z"))
                continue;
            return portal;
        }
        return "none";
    }
}
