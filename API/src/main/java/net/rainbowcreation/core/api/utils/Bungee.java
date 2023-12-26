package net.rainbowcreation.core.api.utils;

import com.google.common.collect.Iterables;
import net.rainbowcreation.core.api.ICore;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

public class Bungee {
    private ICore core;
    private Plugin plugin;
    public Bungee(ICore plugin) {
        core = plugin;
        this.plugin = core.getPlugin();
        this.plugin.getServer().getMessenger().registerOutgoingPluginChannel(this.plugin, "BungeeCord");
        this.plugin.getServer().getMessenger().registerIncomingPluginChannel(this.plugin, "BungeeCord", core.getMessageListener());
    }
    public void sendPlayerToServer(Player player, String serverName) {
        try {
            ByteArrayOutputStream b = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(b);
            out.writeUTF("Connect");
            out.writeUTF(serverName);
            player.sendPluginMessage(plugin, "BungeeCord", b.toByteArray());
            b.close();
            out.close();
        }
        catch (Exception e) {
            player.sendMessage(ChatColor.RED+"Error when trying to connect to "+serverName);
        }
    }

    public int getPlayerCount(String serverName) {
        if (serverName.isEmpty()) {
            serverName = "ALL";
        }
        Player player = Iterables.getFirst(Bukkit.getOnlinePlayers(), null);
        try {
            ByteArrayOutputStream b = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(b);
            out.writeUTF("PlayerCount");
            out.writeUTF(serverName);
            player.sendPluginMessage(plugin, "BungeeCord", b.toByteArray());
            b.close();
            out.close();

            // get input and readInt then return
        }
        catch (Exception ignored) {
        }
        return 0;
    }
}
