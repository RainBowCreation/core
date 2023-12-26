package net.rainbowcreation.core.v1_8_R3.gui;

import net.rainbowcreation.core.api.ICore;
import net.rainbowcreation.core.api.IGui;
import net.rainbowcreation.core.api.utils.Chat;
import net.rainbowcreation.core.v1_8_R3.Core;
import net.rainbowcreation.core.v1_8_R3.utils.Item;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

public class Main implements IGui {
    private ICore core;
    private static Inventory gui = null;
    public static Map<Player, Boolean> is_move = new HashMap<>();

    public Main() {
        core = Core.instance;
    }
    @Override
    public Inventory get() {
        if (gui != null)
            return gui;
        gui = Bukkit.createInventory(core.getGuiHolder(), 54, "Main");
        gui.setItem(44, new Item().material(Material.BARRIER).displayName("<white> ").get());
        gui.setItem(27, new Item().material(Material.STAINED_GLASS_PANE).color(5).displayName("Warps").lore("Left-Click <white>to warp to <green>Lobby").get());
        gui.setItem(28, new Item().material(Material.STAINED_GLASS_PANE).displayName("Mainnet (survival)").lore("Left-Click <white>to warp").lore("<white>recommend version <green>1.20.1+").get());
        gui.setItem(29, new Item().material(Material.STAINED_GLASS_PANE).displayName("RLCraft").lore("Left-Click <white>to warp").lore("<white>need RLCraft version <green>2.9.3").get());
        gui.setItem(30, new Item().material(Material.STAINED_GLASS_PANE).displayName("StoneBlock3").lore("Left-Click <white>to warp").lore("<white>need StoneBlock3 version <green>1.8.1").get());
        gui.setItem(31, new Item().material(Material.STAINED_GLASS_PANE).displayName("<white>Our partner").lore("Left-Click <white>to warp to <green>morphedit.online").lore("<white>recommend version <green>1.20.1+").get());
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
            }.runTaskLater(core.getPlugin(), 1L);
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
        if (server != null) {
            String finalServer = server;
            final int[] count = {0};
            is_move.put(player, false);
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (count[0] == 0)
                        player.closeInventory();
                    if (count[0] < 5) {
                        player.sendTitle(Chat.minimessageColored("<white>Preparing teleport <red>" + (5 - count[0])), Chat.minimessageColored("<red>Do not move"));
                        count[0]++;
                        if (is_move.get(player)) {
                            player.sendMessage(Chat.minimessageColored("<red>Warp Cancelled"));
                            is_move.remove(player);
                            cancel();
                        }
                    }
                    else {
                        core.getBungee().sendPlayerToServer(player, finalServer);
                        is_move.remove(player);
                        cancel();
                    }
                }
            }.runTaskTimer(core.getPlugin(), 1L, 20L);
        }
    }

    @Override
    public boolean isDynamic() {
        return false;
    }
}
