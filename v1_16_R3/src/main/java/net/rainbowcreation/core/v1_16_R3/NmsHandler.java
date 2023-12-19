package net.rainbowcreation.core.v1_16_R3;

import net.minecraft.server.v1_16_R3.IChatBaseComponent;
import net.rainbowcreation.core.api.INms;
import org.bukkit.craftbukkit.v1_16_R3.util.CraftChatMessage;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class NmsHandler implements INms {

    @Override
    public void sendMessage(Player player, String message) {
        for(IChatBaseComponent component : CraftChatMessage.fromString(message)) {
            ((CraftPlayer)player).getHandle().sendMessage(component, player.getUniqueId());
        }
    }

}
