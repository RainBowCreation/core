package net.rainbowcreation.api.utils;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * perm init
 */
public final class RPermission {
    private RPermission() {
        // void
    }

    /**
     *
     * @param player
     * @param permission
     * @return
     */
    public static boolean hasPermission(@NotNull Player player, String permission) {
        return player.hasPermission(permission);
    }

    /**
     *
     * @param player
     * @param group
     * @return
     */
    public static boolean isPlayerInGroup(@NotNull Player player, String group) {
        return player.hasPermission("group." + group);
    }

    /**
     *
     * @param player
     */
    public static void sendPlayerNoPermMessage(Player player) {
        RChat.sendPlayerMessage(player, "<red>You don't have permission!");
    }

    /**
     *
     * @param player
     * @param permission
     * @return
     */
    public static boolean permission(Player player, String permission) {
        if (!hasPermission(player, permission)) {
            sendPlayerNoPermMessage(player);
            return false;
        }
        return true;
    }
}
