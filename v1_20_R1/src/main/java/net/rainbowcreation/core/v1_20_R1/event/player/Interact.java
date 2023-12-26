package net.rainbowcreation.core.v1_20_R1.event.player;

import com.github.puregero.multilib.MultiLib;
import net.rainbowcreation.core.v1_20_R1.recipe.SWarpScroll;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class Interact implements Listener {
    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        final ItemStack itemStack = event.getItem();
        if (itemStack == null)
            return;
        final Player player = event.getPlayer();
        if (MultiLib.isMultiPaper()) {
            if (!MultiLib.isLocalPlayer(player))
                return;
        }
        if (event.getAction().name().contains("RIGHT")) {
            if (itemStack.getType().getKey().getNamespace().equals("warp_scroll")) {
                SWarpScroll.onUse(event);
            }
        }
    }
}
