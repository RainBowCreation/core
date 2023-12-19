package net.rainbowcreation.core.nms.v1_8_R3;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.rainbowcreation.core.api.NMS;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.util.CraftChatMessage;
import org.bukkit.entity.Player;

public class NMSHandler implements NMS {

    @Override
    public void sendMessage(Player player, String message) {
        for(IChatBaseComponent component : CraftChatMessage.fromString(message)) {
            ((CraftPlayer)player).getHandle().sendMessage(component);
        }
    }

}
