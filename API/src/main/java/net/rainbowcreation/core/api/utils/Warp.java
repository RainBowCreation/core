package net.rainbowcreation.core.api.utils;

import com.earth2me.essentials.AsyncTeleport;
import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.Trade;
import com.earth2me.essentials.User;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.plugin.Plugin;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.concurrent.CompletableFuture;

public class Warp {
    private static Plugin pS_plugin;
    private static Essentials pS_ess;

    public Warp(Plugin plugin, Essentials essentials) {
        pS_plugin = plugin;
        pS_ess = essentials;
    }
    public static void S_warp(User player, Location loc, Integer cost) {
        AsyncTeleport teleport = new AsyncTeleport(player, pS_ess);
        teleport.teleportPlayer(player,loc,new Trade(cost, pS_ess), PlayerTeleportEvent.TeleportCause.PLUGIN, new CompletableFuture<>());
    }
}
