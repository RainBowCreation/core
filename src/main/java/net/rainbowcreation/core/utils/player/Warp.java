package net.rainbowcreation.rainbowcreationx.utils.player;

import com.earth2me.essentials.AsyncTeleport;
import com.earth2me.essentials.Trade;
import com.earth2me.essentials.User;
import net.rainbowcreation.rainbowcreationx.RainBowCreationX;
import org.bukkit.Location;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.concurrent.CompletableFuture;

public class Warp {
    public static void warp(User player, Location loc, Integer cost) {
        AsyncTeleport teleport = new AsyncTeleport(player, RainBowCreationX.getInstance().ess);
        teleport.teleportPlayer(player,loc,new Trade(cost, RainBowCreationX.getInstance().ess), PlayerTeleportEvent.TeleportCause.PLUGIN, new CompletableFuture<>());
    }
}
