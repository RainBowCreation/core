package net.rainbowcreation.core.event;

import net.rainbowcreation.core.Core;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class Craft implements Listener {
    private static Core pS_core;

    public Craft(Core plugin) {
        pS_core = plugin;
    }
    @EventHandler
    public void onCraft(PrepareItemCraftEvent event) {
        /*
        ItemStack[] metrix = event.getInventory().getMatrix();
        int j = 0;
        for (ItemStack i : metrix) {
            if (i.getType() == Material.AIR)
                j++;
        }
        */
        pS_core.P_console.info(event.getInventory().getMatrix().toString());
    }
}
