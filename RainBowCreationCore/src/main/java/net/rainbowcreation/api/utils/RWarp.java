package net.rainbowcreation.api.utils;

import com.earth2me.essentials.AsyncTeleport;
import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.Trade;
import com.earth2me.essentials.User;
import org.bukkit.Location;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.concurrent.CompletableFuture;

/**
 * warp
 */
public final class RWarp {
    private RWarp() {
        // void
    }

    /**
     *
     * @param player
     * @param loc
     * @param cost
     * @param ess
     */
    public static void warp(User player, Location loc, Integer cost, Essentials ess) {
        final AsyncTeleport teleport = new AsyncTeleport(player, ess);
        teleport.teleportPlayer(player,loc,new Trade(cost, ess), PlayerTeleportEvent.TeleportCause.PLUGIN, new CompletableFuture<>());
    }
}
