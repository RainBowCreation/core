package net.rainbowcreation.core.gui;

import net.rainbowcreation.core.Core;
import net.rainbowcreation.core.api.bungee.Server;
import net.rainbowcreation.core.api.utils.Item;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class Warp {
    public static Inventory S_get() {
        final Inventory F_inv = Bukkit.createInventory(Core.PS_getInstance().P_guiHolder, 54, "Main");
        F_inv.setItem(44, new Item().material(Material.BARRIER).get());
        F_inv.setItem(27, new Item().material(Material.STAINED_GLASS_PANE).displayName("Warps").lore("Left-Click <white>to warp to <green>Lobby").get());
        F_inv.setItem(28, new Item().material(Material.STAINED_GLASS_PANE).displayName("Survival").lore("Left-Click <white>to warp").lore("<white>recommend version <green>1.20+").get());
        F_inv.setItem(29, new Item().material(Material.STAINED_GLASS_PANE).displayName("RLCraft").lore("Left-Click <white>to warp").lore("<white>need RLCraft version <green>2.9.3").get());
        F_inv.setItem(30, new Item().material(Material.STAINED_GLASS_PANE).displayName("StoneBlock3").lore("Left-Click <white>to warp").lore("<white>need StoneBlock3 version <green>1.8.1").get());
        F_inv.setItem(31, new Item().material(Material.STAINED_GLASS_PANE).displayName("<white>Our partner").lore("Left-Click <white>to warp to <green>morphedit.online").lore("<white>need version <green>1.20.1+").get());
        return F_inv;
    }

    public static void S_onClick(InventoryClickEvent event) {
        event.setCancelled(true);
        final int F_slot = event.getSlot();
        final Player F_player = (Player) event.getWhoClicked();
        String server = null;
        if (F_slot == 27)
            server = "lobby";
        else if (F_slot == 28)
            server = "survival";
        else if (F_slot == 29)
            server = "rlcraft";
        else if (F_slot == 30)
            server = "stoneblock";
        else if (F_slot == 31)
            server = "morph";
        if (server != null)
            Server.sendBungeeCordMessage(F_player, server);
    }
}
