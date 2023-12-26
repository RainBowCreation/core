package net.rainbowcreation.core.event.inventory;

import net.rainbowcreation.core.Core;
import net.rainbowcreation.core.api.IGui;
import net.rainbowcreation.core.api.utils.GuiHolder;
import net.rainbowcreation.core.gui.Gui;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;

public class Click implements Listener {
    @EventHandler
    public void onCLick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player))
            return;
        Player player = (Player) event.getWhoClicked();
        final ClickType F_clicktype = event.getClick();
        if (F_clicktype != ClickType.LEFT)
            return;
        if (event.getCursor().getType() != Material.AIR)
            return;
        final Inventory F_clickedInventory = event.getInventory();
        final int F_clickedSlot = event.getRawSlot();
        if (F_clickedSlot == -999) {
            event.setCancelled(true);
            if (F_clickedInventory.getHolder() instanceof GuiHolder) {
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        player.closeInventory();
                    }
                }.runTaskLater(Core.getInstance(), 1L);
            }
            else {
                player.openInventory(((IGui) Gui.instance.get("gui_main")).getDynamic(player));
            }
            return;
        }
        if (F_clickedSlot < F_clickedInventory.getSize()) {
            // Click happened in the top inventory
            // Additional logic based on the clicked inventory and slot
            if (F_clickedInventory.getHolder() instanceof GuiHolder) {
                ((IGui) Gui.instance.get("gui_main")).onClick(event);
                return;
            }
        } else {
            // Click happened in the player's inventory or a bottom inventory
        }
    }
}
