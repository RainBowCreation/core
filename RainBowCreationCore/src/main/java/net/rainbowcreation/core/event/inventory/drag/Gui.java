package net.rainbowcreation.core.event.inventory.drag;

import net.rainbowcreation.api.utils.RGui;
import net.rainbowcreation.api.utils.RLst;
import net.rainbowcreation.core.Core;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.DragType;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;

/**
 * guidrag
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
    public static void onEvent(InventoryDragEvent event) {
        if (!event.getInventory().toString().contains("InventoryCustom@"))
            return;
        if (event.getType() != DragType.EVEN)
            return;
        final Inventory inv = event.getInventory();
        final List<List<Integer>> numset = RGui.getNumset(RGui.getID(inv));
        final List<Integer> slots = new ArrayList<>();
        //Map<Integer, ItemStack> items = event.getNewItems();
        slots.addAll(event.getRawSlots());
        for (Integer slot : slots) {
            if (slot > 53)
                continue;
            final int[] ij = RLst.toIJ(slot);
            final int j = numset.get(ij[0]).get(ij[1]);
            if (j != 0) {
                event.setCancelled(true);
                return;
            }
        }
    }
}
