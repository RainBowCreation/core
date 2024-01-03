package net.rainbowcreation.core.event.packet;

import com.github.retrooper.packetevents.event.PacketListenerAbstract;
import com.github.retrooper.packetevents.event.PacketListenerPriority;
import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.protocol.player.User;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientChatMessage;
import net.rainbowcreation.core.Core;
import net.rainbowcreation.core.api.utils.Chat;

public class Receive extends PacketListenerAbstract {
    public Receive() {
        super(PacketListenerPriority.LOW);
    }

    @Override
    public void onPacketReceive(PacketReceiveEvent event) {
        //Cross-platform user
        User user = event.getUser();
        if (event.getPacketType() == PacketType.Play.Client.CHAT_MESSAGE) {
            WrapperPlayClientChatMessage chatMessage = new WrapperPlayClientChatMessage(event);
            String message = chatMessage.getMessage();
            Core.getInstance().getConsole().info(message);
            if (message.equalsIgnoreCase("cv?")) {
                user.sendMessage(Chat.minimessageComponent("<yellow>" + "Your client version: <green>"
                        + user.getClientVersion().getReleaseName() + "."));
                event.setCancelled(true);
            }
        }
    }
}
