package net.rainbowcreation.core.v1_8_R3.event.inventory;

import net.rainbowcreation.core.api.ICore;
import net.rainbowcreation.core.api.utils.Action;
import net.rainbowcreation.core.api.utils.GuiHolder;
import net.rainbowcreation.core.v1_8_R3.Core;
import net.rainbowcreation.core.v1_8_R3.gui.Gui;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class Click implements Listener {
    private ICore core;

    public Click() {
        core = Core.instance;
    }
    @EventHandler
    public void onCLick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player))
            return;
        final Player player = (Player) event.getWhoClicked();
        final Inventory clickedInventory = event.getInventory();
        final int clickedSlot = event.getRawSlot();
        if (clickedSlot == -999) {
            final ClickType clicktype = event.getClick();
            if (clicktype != ClickType.LEFT)
                return;
            if (event.getCursor().getType() != Material.AIR)
                return;
            event.setCancelled(true);
            if (clickedInventory.getHolder() instanceof GuiHolder) {
                Action.closePlayerInventory(core.getPlugin(), player);
            }
            else {
                player.openInventory(Gui.MAIN.getDynamic(player));
            }
            return;
        }
        if (clickedSlot < clickedInventory.getSize()) {
            // Click happened in the top inventory
            // Additional logic based on the clicked inventory and slot
            if (clickedInventory.getHolder() instanceof GuiHolder) {
                Gui.MAIN.onClick(event);
            }
        } else {
            // Click happened in the player's inventory or a bottom inventory
        }
    }
}
