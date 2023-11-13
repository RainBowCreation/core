package net.rainbowcreation.core.eventmanager.inventorydrag;

import net.rainbowcreation.core.gui.Gui;
import net.rainbowcreation.core.utils.math.lst.Lst;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.DragType;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;

public class GuiDrag implements Listener {
    @EventHandler
    public static void onEvent(InventoryDragEvent event) {
        if (!event.getInventory().toString().contains("InventoryCustom@"))
            return;
        if (event.getType() != DragType.EVEN)
            return;
        Inventory inv = event.getInventory();
        List<List<Integer>> numset = Gui.getNumset(Gui.getID(inv));
        List<Integer> slots = new ArrayList<>();
        //Map<Integer, ItemStack> items = event.getNewItems();
        slots.addAll(event.getRawSlots());
        for (Integer slot : slots) {
            if (slot > 53)
                continue;
            int[] ij = Lst.toIJ(slot);
            int j = numset.get(ij[0]).get(ij[1]);
            if (j != 0) {
                event.setCancelled(true);
                return;
            }
        }
    }
}