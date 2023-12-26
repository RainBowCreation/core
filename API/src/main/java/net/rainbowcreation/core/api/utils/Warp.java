package net.rainbowcreation.core.api.utils;

import com.earth2me.essentials.AsyncTeleport;
import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.Trade;
import com.earth2me.essentials.User;
import org.bukkit.Location;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.plugin.Plugin;

import java.util.concurrent.CompletableFuture;

public class Warp {
    private static Plugin plugin;
    private static Essentials ess;

    public Warp(Plugin plugin, Essentials essentials) {
        Warp.plugin = plugin;
        ess = essentials;
    }
    public static void warp(User player, Location loc, Integer cost) {
        AsyncTeleport teleport = new AsyncTeleport(player, ess);
        teleport.teleportPlayer(player,loc,new Trade(cost, ess), PlayerTeleportEvent.TeleportCause.PLUGIN, new CompletableFuture<>());
    }
}
