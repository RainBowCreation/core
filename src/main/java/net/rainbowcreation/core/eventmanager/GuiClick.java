package net.rainbowcreation.core.eventmanager;

import net.rainbowcreation.core.Core;
import net.rainbowcreation.core.gui.Gui;
import net.rainbowcreation.core.utils.math.Lst;
import net.rainbowcreation.core.utils.math.Roman;
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

public class GuiClick implements Listener {
    private static final Core plugin = Core.getInstance();
    private static final FileConfiguration config = plugin.guiData.getConfig();

    @EventHandler
    public static void onEvent(InventoryClickEvent event) {
        Inventory inv = event.getClickedInventory();
        if (inv == null)
            return;
        ClickType click = event.getClick();
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
        int guiID = Gui.getID(inv);
        List<List<Integer>> numset = Gui.getNumset(guiID);
        int[] ij;
        ij = Lst.toIJ(slot);
        slot = numset.get(ij[0]).get(ij[1]);
        List<ClickType> allow = Arrays.asList(ClickType.LEFT, ClickType.RIGHT, ClickType.SHIFT_LEFT, ClickType.SHIFT_RIGHT);
        event.setCancelled(true);
        if (allow.contains(click))
            event.setCancelled(Gui.getCancelled(slot));
        doOnClick(event, guiID);
        if (click == ClickType.LEFT && slot>1 && slot <200) { //if leftclick at anyGui logo open it gui to player
            reload(event, slot);
        }
    }
    public static int recheckID(InventoryClickEvent event, int guiID) {
        if (guiID != 199)
            return guiID;
        String name = event.getView().getTitle().substring(7);
        for (String key: config.getKeys(true)) { //Linear search may cause low perfomance
            if (!key.endsWith(".name"))
                continue;
            if (config.get(key).toString().equals(name)) {
                guiID = Roman.deseialize(key.substring(0, key.indexOf(".")));
                break;
            }
        }
        return guiID;
    }
    public static void  reload(InventoryClickEvent event) {
        int guiID = Gui.getID(event.getClickedInventory());
        event.getWhoClicked().openInventory(new Gui(recheckID(event,guiID)).setPlayer((Player) event.getWhoClicked()).get());
    }
    public static void  reload(InventoryClickEvent event, int guiID) {
        event.getWhoClicked().openInventory(new Gui(recheckID(event, guiID)).setPlayer((Player) event.getWhoClicked()).get());
    }


    private static void doOnClick(InventoryClickEvent event, int guiID) {
        try { //Handle click of any gui page redirect to specific classGui
            Class<?> gui = Class.forName("net.rainbowcreation.core.gui."+config.getString( Roman.serialize(recheckID(event, guiID))+".name")+"Gui");
            gui.getDeclaredMethod("onClick", InventoryClickEvent.class).invoke(gui.newInstance(), event);
        } catch (Exception ignored) {}
    }

    public static void openGui(InventoryClickEvent event) {
        if (event.getClick() == ClickType.LEFT)
            return;
        ItemStack item = event.getCurrentItem();
        if (item.getType() != Material.BARRIER)
            return;
        if (!item.hasItemMeta())
            return;
        ItemMeta itemMeta = item.getItemMeta();
        if (!itemMeta.hasCustomModelData())
            return;
        Inventory inv = event.getClickedInventory();
        int id = Gui.getID(inv);
        List<List<Integer>> numset = Gui.getNumset(id);
        int[] ij = Lst.toIJ(event.getSlot());
        int i = numset.get(ij[0]).get(ij[1]);
        if (i == id)
            return;
        if (i > 1 && i < 200)
        { //other gui icon clicked?
            doOnClick(event, i);
        }
    }
}
