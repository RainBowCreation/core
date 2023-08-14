package net.rainbowcreation.core.gui;

import net.rainbowcreation.core.core;
import net.rainbowcreation.core.eventmanager.GuiClick;
import net.rainbowcreation.core.utils.item.Item;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class MainGui {
    private final core plugin = core.getInstance();
    public Item runSelf(Gui gui, Item item) {
        //todo
        return item;
    }
    public Inventory run(Gui gui) {
        Inventory inv = gui.getInv();
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
        ItemStack item = event.getCurrentItem();
        ClickType clickType = event.getClick();
        ItemStack check = Gui.guiPart(2);
        Inventory inv = event.getClickedInventory();

        if (item.equals(check)) { //click self icon
            if (clickType == ClickType.LEFT) { //leftclick open that gui
                Map<Integer, ItemStack> craftinggrid = new HashMap<>();
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
        GuiClick.openGui(event); //redirect to other class if not in this handler
    }
}
