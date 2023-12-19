package net.rainbowcreation.core.event.inventory.click;

import net.rainbowcreation.api.utils.RGui;
import net.rainbowcreation.api.utils.RLst;
import net.rainbowcreation.api.utils.RRoman;
import net.rainbowcreation.core.Core;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

/**
 * guiclick
 */
public class Gui implements Listener {
    private Core plugin;
    private static FileConfiguration config;

    public Gui(Core plugin) {
        this.plugin = plugin;
        config = plugin.guiData.getConfig();
    }
    /**
     *
     * @param event
     */

    @EventHandler
    public static void onEvent(InventoryClickEvent event) {
        final Inventory inv = event.getClickedInventory();
        if (inv == null)
            return;
        final ClickType click = event.getClick();
        if (event.getView().getTitle().startsWith("[GUI]  ") && click == ClickType.SHIFT_LEFT) {
            event.setCancelled(true);
            return;
        }
        if (!inv.toString().contains("InventoryCustom@"))
            return;
        int slot = event.getSlot();
        if (slot == 44) {
            event.setCancelled(true);
            return;
        }
        final int guiID = RGui.getID(inv);
        final List<List<Integer>> numset = RGui.getNumset(guiID);
        final int[] ij;
        ij = RLst.toIJ(slot);
        slot = numset.get(ij[0]).get(ij[1]);
        final List<ClickType> allow = Arrays.asList(ClickType.LEFT, ClickType.RIGHT, ClickType.SHIFT_LEFT, ClickType.SHIFT_RIGHT);
        event.setCancelled(true);
        if (allow.contains(click))
            event.setCancelled(RGui.getCancelled(slot));
        doOnClick(event, guiID);
        if (click == ClickType.LEFT && slot>1 && slot <200) { //if leftclick at anyGui logo open it gui to player
            reload(event, slot);
        }
    }

    /**
     *
     * @param event
     * @param guiID
     * @return
     */
    public static int recheckID(InventoryClickEvent event, int guiID) {
        if (guiID != 199)
            return guiID;
        final String name = event.getView().getTitle().substring(7);
        for (String key: config.getKeys(true)) { //Linear search may cause low perfomance
            if (!key.endsWith(".name"))
                continue;
            if (config.get(key).toString().equals(name)) {
                guiID = RRoman.deseialize(key.substring(0, key.indexOf(".")));
                break;
            }
        }
        return guiID;
    }

    /**
     *
     * @param event
     */
    public static void reload(InventoryClickEvent event) {
        final int guiID = RGui.getID(event.getClickedInventory());
        event.getWhoClicked().openInventory(new RGui(recheckID(event,guiID), config).setPlayer((Player) event.getWhoClicked()).get());
    }

    /**
     *
     * @param event
     * @param guiID
     */
    public static void reload(InventoryClickEvent event, int guiID) {
        event.getWhoClicked().openInventory(new RGui(recheckID(event, guiID), config).setPlayer((Player) event.getWhoClicked()).get());
    }

    /**
     *
     * @param event
     * @param guiID
     */
    private static void doOnClick(InventoryClickEvent event, int guiID) {
        try { //Handle click of any gui page redirect to specific classGui
            final Class<?> gui = Class.forName("net.rainbowcreation.core.gui."+config.getString( RRoman.serialize(recheckID(event, guiID))+".name"));
            gui.getDeclaredMethod("onClick", InventoryClickEvent.class).invoke(gui.newInstance(), event);
        } catch (Exception ignored) {}
    }

    /**
     *
     * @param event
     */
    public static void openGui(InventoryClickEvent event) {
        if (event.getClick() == ClickType.LEFT)
            return;
        final ItemStack item = event.getCurrentItem();
        if (item.getType() != Material.BARRIER)
            return;
        if (!item.hasItemMeta())
            return;
        final ItemMeta itemMeta = item.getItemMeta();
        if (!itemMeta.hasCustomModelData())
            return;
        final Inventory inv = event.getClickedInventory();
        final int id = RGui.getID(inv);
        final List<List<Integer>> numset = RGui.getNumset(id);
        final int[] ij = RLst.toIJ(event.getSlot());
        final int i = numset.get(ij[0]).get(ij[1]);
        if (i == id)
            return;
        if (i > 1 && i < 200) { //other gui icon clicked?
            doOnClick(event, i);
        }
    }
}
