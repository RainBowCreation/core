package net.rainbowcreation.core.api.bungee;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Server {
    public static void sendBungeeCordMessage(Plugin plugin, Player player, String serverName) {
        ByteArrayOutputStream byteArrayOutput = new ByteArrayOutputStream();
        DataOutputStream dataOutput = new DataOutputStream(byteArrayOutput);

        try {
            // Write the name of the subchannel
            dataOutput.writeUTF("BungeeCord");

            // Write the name of the action you want to perform (in this case, "Connect")
            dataOutput.writeUTF("Connect");

            // Write the server name you want to connect to
            dataOutput.writeUTF(serverName);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Send the plugin message to the player
        player.sendPluginMessage(plugin, "BungeeCord", byteArrayOutput.toByteArray());
    }
}
