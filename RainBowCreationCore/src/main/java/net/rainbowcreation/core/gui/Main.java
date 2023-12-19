package net.rainbowcreation.core.gui;

import net.rainbowcreation.api.utils.RGui;
import net.rainbowcreation.api.utils.RItem;
import net.rainbowcreation.core.Core;
import net.rainbowcreation.core.event.inventory.click.Gui;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

/**
 * main
 */
public class Main {
    private static Core plugin;

    public Main() {
        plugin = Core.getInstance();
    }

    /**
     *
     * @param gui
     * @param item
     * @return
     */
    public RItem runSelf(RGui gui, RItem item) {
        //todo
        return item;
    }

    /**
     *
     * @param gui
     * @return
     */
    public Inventory run(RGui gui) {
        final Inventory inv = gui.getInv();
        //todo
        return inv;
    }

    /**
     * Handel Click event of it own page.
     * redirect event to other page if it not.
     *
     * @param event {@link InventoryClickEvent}
     *
     */
    public void onClick(InventoryClickEvent event) {
        final ItemStack item = event.getCurrentItem();
        final ClickType clickType = event.getClick();
        final ItemStack check = RGui.guiPart(2);
        final Inventory inv = event.getClickedInventory();

        if (item.equals(check)) { //click self icon
            if (clickType == ClickType.LEFT) { //leftclick open that gui
                final Map<Integer, ItemStack> craftinggrid = new HashMap<>();
                craftinggrid.put(0, inv.getItem(12));
                craftinggrid.put(1, inv.getItem(13));
                craftinggrid.put(2, inv.getItem(21));
                craftinggrid.put(3, inv.getItem(22));

                return;
            } else {
                //if right-click or some else
                //todo
            }
            return;
        }
        Gui.openGui(event); //redirect to other class if not in this handler
    }
}
