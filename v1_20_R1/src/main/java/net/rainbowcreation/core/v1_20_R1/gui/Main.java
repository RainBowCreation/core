package net.rainbowcreation.core.v1_20_R1.gui;

import net.rainbowcreation.core.api.IGui;
import net.rainbowcreation.core.api.utils.Item;
import net.rainbowcreation.core.v1_20_R1.Core;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;

public class Main implements IGui {
    private static Inventory gui = null;
    @Override
    public Inventory get() {
        if (gui != null)
            return gui;
        gui = Bukkit.createInventory(Core.instance.getGuiHolder(), 54, "Main");
        gui.setItem(44, new Item().material(Material.BARRIER).customModelData(1).displayName("<white>").get());
        gui.setItem(27, new Item().material(Material.LIME_STAINED_GLASS_PANE).displayName("Warps").lore("Left-Click <white>to warp to <green>Lobby").get());
        gui.setItem(28, new Item().material(Material.WHITE_STAINED_GLASS_PANE).displayName("Mainnet (survival)").lore("Left-Click <white>to warp").lore("<white>recommend version <green>1.20.1+").get());
        gui.setItem(29, new Item().material(Material.WHITE_STAINED_GLASS_PANE).displayName("RLCraft").lore("Left-Click <white>to warp").lore("<white>need RLCraft version <green>2.9.3").get());
        gui.setItem(30, new Item().material(Material.WHITE_STAINED_GLASS_PANE).displayName("StoneBlock3").lore("Left-Click <white>to warp").lore("<white>need StoneBlock3 version <green>1.8.1").get());
        gui.setItem(31, new Item().material(Material.WHITE_STAINED_GLASS_PANE).displayName("<white>Our partner").lore("Left-Click <white>to warp to <green>morphedit.online").lore("<white>recommend version <green>1.20.1+").get());
        return gui;
    }

    @Override
    public Inventory getDynamic(Player player) {
        if (!isDynamic())
            return get();
        return null;
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        event.setCancelled(true);
        final int slot = event.getSlot();
        final Player player = (Player) event.getWhoClicked();
        if (slot == 44) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    player.closeInventory();
                }
            }.runTaskLater(Core.plugin, 1L);
            return;
        }
        String server = null;
        if (slot == 27)
            server = "lobby";
        else if (slot == 28)
            server = "mainnet";
        else if (slot == 29)
            server = "rlcraft";
        else if (slot == 30)
            server = "stoneblock";
        else if (slot == 31)
            server = "morph";
        if (server != null)
            Core.instance.getBungee().sendBungeeCordMessage(player, server);
    }

    @Override
    public boolean isDynamic() {
        return false;
    }
}