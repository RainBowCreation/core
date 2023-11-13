package net.rainbowcreation.core.utils.player.warp;

import com.earth2me.essentials.AsyncTeleport;
import com.earth2me.essentials.Trade;
import com.earth2me.essentials.User;
import net.rainbowcreation.core.Core;
import org.bukkit.Location;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.concurrent.CompletableFuture;

public class Warp {
    private static final Core plugin = Core.getInstance();
    public static void warp(User player, Location loc, Integer cost) {
        AsyncTeleport teleport = new AsyncTeleport(player, plugin.ess);
        teleport.teleportPlayer(player,loc,new Trade(cost, plugin.ess), PlayerTeleportEvent.TeleportCause.PLUGIN, new CompletableFuture<>());
    }
}
