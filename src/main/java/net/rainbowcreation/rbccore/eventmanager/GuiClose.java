package net.rainbowcreation.rainbowcreationx.eventmanager;

import net.rainbowcreation.rainbowcreationx.gui.Gui;
import net.rainbowcreation.rainbowcreationx.utils.math.Lst;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class GuiClose implements Listener {
    @EventHandler
    public static void onEvent(InventoryCloseEvent event) {
        if (!event.getInventory().toString().contains("InventoryCustom@"))
            return;
        Inventory inv = event.getInventory();
        int guiID = Gui.getID(inv);
        List<List<Integer>> numset = Gui.getNumset(guiID);
        Inventory pInv = event.getPlayer().getInventory();
        for (int i = 0;i<6;i++) {
            List<Integer> tmp = numset.get(i);
            for (int j = 0;j<9;j++) {
                if (tmp.get(j) == 0) {
                    ItemStack item = inv.getItem(Lst.toSlot(i, j));
                    if (item != null)
                        pInv.addItem(item);
                }
            }
        }
    }
}
