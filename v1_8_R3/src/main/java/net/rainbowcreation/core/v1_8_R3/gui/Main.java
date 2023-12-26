package net.rainbowcreation.core.v1_8_R3.gui;

import net.rainbowcreation.core.api.IGui;
import net.rainbowcreation.core.api.utils.Item;
import net.rainbowcreation.core.v1_8_R3.Core;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class Main implements IGui {
    private static Inventory gui = null;
    @Override
    public Inventory get() {
        if (gui != null)
            return gui;
        gui = Bukkit.createInventory(Core.instance.getGuiHolder(), 54, "Main");
        gui.setItem(44, new Item().material(Material.BARRIER).get());
        gui.setItem(27, new Item().material(Material.STAINED_GLASS_PANE).displayName("Warps").lore("Left-Click <white>to warp to <green>Lobby").get());
        gui.setItem(28, new Item().material(Material.STAINED_GLASS_PANE).displayName("Survival").lore("Left-Click <white>to warp").lore("<white>recommend version <green>1.20+").get());
        gui.setItem(29, new Item().material(Material.STAINED_GLASS_PANE).displayName("RLCraft").lore("Left-Click <white>to warp").lore("<white>need RLCraft version <green>2.9.3").get());
        gui.setItem(30, new Item().material(Material.STAINED_GLASS_PANE).displayName("StoneBlock3").lore("Left-Click <white>to warp").lore("<white>need StoneBlock3 version <green>1.8.1").get());
        gui.setItem(31, new Item().material(Material.STAINED_GLASS_PANE).displayName("<white>Our partner").lore("Left-Click <white>to warp to <green>morphedit.online").lore("<white>need version <green>1.20.1+").get());
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
            Core.instance.getBungee().sendBungeeCordMessage(F_player, server);
    }

    @Override
    public boolean isDynamic() {
        return false;
    }
}
