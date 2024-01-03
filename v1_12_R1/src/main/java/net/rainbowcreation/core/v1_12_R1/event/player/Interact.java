package net.rainbowcreation.core.v1_12_R1.event.player;

import net.rainbowcreation.core.v1_12_R1.recipe.SWarpScroll;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class Interact implements Listener {
    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        final ItemStack itemStack = event.getItem();
        if (itemStack == null)
            return;

        final Player player = event.getPlayer();
        if (event.getAction().name().contains("RIGHT")) {
           final List<String> lorelist = itemStack.getItemMeta().getLore();
            if (lorelist == null)
                return;
            if (lorelist.get(lorelist.size() - 1).contains("wiki.rainbowcreation.net/item/warp_scroll")) {
                SWarpScroll.onUse(event);
            }
        }
    }
}
