package net.rainbowcreation.core.event.inventory.close;

import net.rainbowcreation.api.utils.RGui;
import net.rainbowcreation.api.utils.RLst;
import net.rainbowcreation.core.Core;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * guiclose
 */
public class Gui implements Listener {
    private final Core plugin;

    public Gui(Core plugin) {
        this.plugin = plugin;
    }

    /**
     *
     * @param event
     */
    @EventHandler
    public static void onEvent(InventoryCloseEvent event) {
        if (!event.getInventory().toString().contains("InventoryCustom@"))
            return;
        final Inventory inv = event.getInventory();
        final int guiID = RGui.getID(inv);
        final List<List<Integer>> numset = RGui.getNumset(guiID);
        final Inventory pInv = event.getPlayer().getInventory();
        for (int i = 0;i<6;i++) {
            final List<Integer> tmp = numset.get(i);
            for (int j = 0;j<9;j++) {
                if (tmp.get(j) == 0) {
                    final ItemStack item = inv.getItem(RLst.toSlot(i, j));
                    if (item != null)
                        pInv.addItem(item);
                }
            }
        }
    }
}
