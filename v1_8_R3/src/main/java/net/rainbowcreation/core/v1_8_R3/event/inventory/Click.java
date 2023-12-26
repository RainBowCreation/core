package net.rainbowcreation.core.v1_8_R3.event.inventory;

import net.rainbowcreation.core.api.ICore;
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
import org.bukkit.scheduler.BukkitRunnable;

public class Click implements Listener {
    private ICore core;

    public Click() {
        core = Core.instance;
    }
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
                }.runTaskLater(core.getPlugin(), 1L);
            }
            else {
                player.openInventory(Gui.MAIN.getDynamic(player));
            }
            return;
        }
        if (F_clickedSlot < F_clickedInventory.getSize()) {
            // Click happened in the top inventory
            // Additional logic based on the clicked inventory and slot
            if (F_clickedInventory.getHolder() instanceof GuiHolder) {
                Gui.MAIN.onClick(event);
                return;
            }
        } else {
            // Click happened in the player's inventory or a bottom inventory
        }
    }
}
