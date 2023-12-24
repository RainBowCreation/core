package net.rainbowcreation.core.api.bungee;

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
        ByteArrayOutputStream byteArrayOutput = new ByteArrayOutputStream();
        DataOutputStream dataOutput = new DataOutputStream(byteArrayOutput);

        try {
            // Write the name of the subchannel
            dataOutput.writeUTF("BungeeCord");

            // Write the name of the action you want to perform (in this case, "Connect")
            dataOutput.writeUTF("Connect");

            // Write the server name you want to connect to
            dataOutput.writeUTF(serverName);
            pS_plugin.getLogger().info("done");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Send the plugin message to the player
        player.sendPluginMessage(pS_plugin, "BungeeCord", byteArrayOutput.toByteArray());
    }
}
