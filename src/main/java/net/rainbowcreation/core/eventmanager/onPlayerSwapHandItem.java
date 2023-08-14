package net.rainbowcreation.core.eventmanager;

import net.rainbowcreation.core.core;
import net.rainbowcreation.core.gui.Gui;
import net.rainbowcreation.core.utils.permission.Permission;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

public class onPlayerSwapHandItem implements Listener {
    @EventHandler
    public static void onEvent(PlayerSwapHandItemsEvent event) {
        Player player = event.getPlayer();
        if (!Permission.hasPermission(player, "rbc.verified"))
            return;
        if (!core.getInstance().playerData.getConfig().getBoolean("player." + player.getUniqueId() + ".f_gui") ^ player.isSneaking())
            return;
        event.setCancelled(true);
        player.openInventory(new Gui(2).setPlayer(player).get());
    }
}
