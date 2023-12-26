package net.rainbowcreation.core.message;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BungeeListener implements PluginMessageListener {
    private List<String> server_list = Arrays.asList("mainnet");
    private Map<String, Integer> player_count = new HashMap<>();

    @Override
    public void onPluginMessageReceived(@NotNull String channel, @NotNull Player player, @NotNull byte[] message) {
        if (!channel.equals("BungeeCord")) {
            return;
        }

        ByteArrayDataInput in = ByteStreams.newDataInput(message);
        String subChannel = in.readUTF();


        if (subChannel.equals("PlayerCountResponse")) {
            String targetServer = in.readUTF();
            int playerCount = in.readInt();

            // Handle the received player count
            System.out.println("Player count on server " + targetServer + ": " + playerCount);
        }
    }
}
