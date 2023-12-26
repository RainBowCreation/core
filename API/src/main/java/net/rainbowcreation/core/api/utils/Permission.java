package net.rainbowcreation.core.api.utils;

import org.bukkit.entity.Player;

@SuppressWarnings("unused")
public class Permission {
    public static boolean hasPermission(Player player, String permission) {
        return player.hasPermission(permission);
    }
    public static boolean isPlayerInGroup(Player player, String group) {
        return player.hasPermission("group." + group);
    }
    public static void sendPlayerNoPermMessage(Player player) {
        Chat.sendPlayerMessage(player, "<red>You don't have permission!");
    }
    public static boolean permission(Player player, String permission) {
        if (!hasPermission(player, permission)) {
            sendPlayerNoPermMessage(player);
            return false;
        }
        return true;
    }
}
