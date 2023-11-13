package net.rainbowcreation.core.gui.setting;

import net.rainbowcreation.core.Core;
import net.rainbowcreation.core.eventmanager.inventoryclick.GuiClick;
import net.rainbowcreation.core.gui.Gui;
import net.rainbowcreation.core.utils.item.Item;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class Setting {
    private static final Core plugin = Core.getInstance();
    private final FileConfiguration config = plugin.playerData.getConfig();
    public Item runSelf(Gui gui, Item item) {
        //todo
        String key = "<white>";
        if (!config.getBoolean("player."+gui.getPlayer().getUniqueId()+".f_gui"))
            key += "Shift+";
        key += "F (or swap offhand key)";
        item.lore("<gray>Current key: "+key);
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
        ItemStack check = Gui.guiPart(3);

        if (item.equals(check)) { //click self icon
            switch (clickType) {
                case LEFT -> {
                    //todo
                }
                case RIGHT -> {
                    String prefix = "player." + event.getWhoClicked().getUniqueId() + ".f_gui";
                    config.set(prefix, !config.getBoolean(prefix));
                    plugin.playerData.saveConfig();
                    plugin.playerData.reloadConfig();
                    GuiClick.reload(event);
                }
            }
            return;
        }
        GuiClick.openGui(event); //redirect to other class if not in this handler
    }
}
