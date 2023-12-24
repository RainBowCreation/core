package net.rainbowcreation.core.api.bungee;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Server {
    private static Plugin pS_plugin;
    public Server(Plugin plugin, org.bukkit.Server server) {
        pS_plugin = plugin;
        server.getMessenger().registerOutgoingPluginChannel(pS_plugin, "BungeeCord");
    }
    public static void sendBungeeCordMessage(Player player, String serverName) {
        try {
            ByteArrayOutputStream b = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(b);
            out.writeUTF("Connect");
            out.writeUTF(serverName);
            player.sendPluginMessage(pS_plugin, "BungeeCord", b.toByteArray());
            b.close();
            out.close();
        }
        catch (Exception e) {
            player.sendMessage(ChatColor.RED+"Error when trying to connect to "+serverName);
        }
    }
}
