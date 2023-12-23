package net.rainbowcreation.core.gui;

import net.rainbowcreation.core.api.gui.Click;
import net.rainbowcreation.core.api.utils.Gui;
import net.rainbowcreation.core.api.utils.Item;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;

public class Main {
    private static Plugin plugin;
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
        ItemStack check = Gui.S_guiPart(2, new Gui(2));
        Inventory inv = event.getInventory();

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
        Click.openGui(event); //redirect to other class if not in this handler
    }
}