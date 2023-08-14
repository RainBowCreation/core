package net.rainbowcreation.core.utils.player;

import com.earth2me.essentials.AsyncTeleport;
import com.earth2me.essentials.Trade;
import com.earth2me.essentials.User;
import net.rainbowcreation.core.core;
import org.bukkit.Location;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.concurrent.CompletableFuture;

public class Warp {
    public static void warp(User player, Location loc, Integer cost) {
        AsyncTeleport teleport = new AsyncTeleport(player, core.getInstance().ess);
        teleport.teleportPlayer(player,loc,new Trade(cost, core.getInstance().ess), PlayerTeleportEvent.TeleportCause.PLUGIN, new CompletableFuture<>());
    }
}
