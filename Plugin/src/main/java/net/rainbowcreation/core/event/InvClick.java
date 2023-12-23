package net.rainbowcreation.core.event;

import net.rainbowcreation.core.Core;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InvClick implements Listener {
    @EventHandler
    public void onCLick(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player)
            Core.PS_getInstance().P_console.info(event.getInventory().getName() + " slot " + event.getSlot());
    }
}
