package net.rainbowcreation.core.v1_20_R1.gui;

import net.kyori.adventure.title.Title;
import net.md_5.bungee.api.chat.TextComponent;
import net.rainbowcreation.core.api.ICore;
import net.rainbowcreation.core.api.IGui;
import net.rainbowcreation.core.api.utils.Action;
import net.rainbowcreation.core.api.utils.Chat;
import net.rainbowcreation.core.api.utils.Item;
import net.rainbowcreation.core.v1_20_R1.Core;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

public class Main implements IGui {
    private final ICore core;
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
        gui.setItem(44, new Item().material(Material.BARRIER).customModelData(3).displayName(" ").get());

        // resourcepack zone
        gui.setItem(0, new Item().material(Material.RED_STAINED_GLASS_PANE).displayName("Graphic").lore("Left-Click <white>to auto detect versions").lore("<red>Currently under development").get());
        gui.setItem(1, new Item().material(Material.WHITE_STAINED_GLASS_PANE).displayName("other versions").lore("<gray>visit our github at <white>rainbowcreation.net/git").get());
        gui.setItem(2, new Item().material(Material.WHITE_STAINED_GLASS_PANE).displayName("1.11-1.12.2").lore("Left-Click <white>to use").lore("Right-Click <white>to use lite version").lore("<green>RLCraft <white>Support!").get());
        gui.setItem(3, new Item().material(Material.WHITE_STAINED_GLASS_PANE).displayName("1.15-1.16.5").lore("Left-Click <white>to use").lore("Right-Click <white>to use lite version").get());
        gui.setItem(4, new Item().material(Material.WHITE_STAINED_GLASS_PANE).displayName("1.18").lore("Left-Click <white>to use").lore("Right-Click <white>to use lite version").lore("<green>StoneBlock3 <white>Support!").get());
        gui.setItem(5, new Item().material(Material.WHITE_STAINED_GLASS_PANE).displayName("1.19").lore("Left-Click <white>to use").lore("Right-Click <white>to use lite version").get());
        gui.setItem(6, new Item().material(Material.WHITE_STAINED_GLASS_PANE).displayName("1.20").lore("Left-Click <white>to use").lore("Right-Click <white>to use lite version").get());

        // warp
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
        final ClickType clickType = event.getClick();
        if (!(clickType == ClickType.LEFT || clickType == ClickType.RIGHT))
            return;
        final int slot = event.getSlot();
        final Player player = (Player) event.getWhoClicked();
        if (slot >= 0 & slot <= 6) {
            String url = null;
            switch (slot) {
                case (0) : {
                    Action.sendPlayerMessage(player, "<red>Sorry this option is not available");
                    break;
                }
                case (1) : {
                    Action.sendPlayerMessage(player, "Check and download your resourcepack here");
                    player.spigot().sendMessage(new TextComponent("https://github.com/RainBowCreation/resourcepack/releases/latest"));
                    break;
                }
                case (2) : {
                    url = "v1_11";
                    break;
                }
                case (3) : {
                    url = "v1_15";
                    break;
                }
                case (4) : {
                    url = "v1_18";
                    break;
                }
                case (5) : {
                    url = "v1_19";
                    break;
                }
                case (6) : {
                    url = "v1_20";
                    break;
                }
            }
            Action.closePlayerInventory(core.getPlugin(), player);
            if (url == null)
                return;
            if (clickType == ClickType.RIGHT)
                url+="_lite";
            url = "https://github.com/RainBowCreation/resourcepack/releases/latest/download/RainBowCreation_" + url + ".zip";
            final int[] count = {0};
            final String finalUrl = url;
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (count[0] == 0) {
                        player.sendTitle(Chat.minimessageColored("<green>Downloading resourcepack..."), Chat.minimessageColored("You can download manually at our github."));
                        count[0]++;
                    } else if (count[0] >= 1) {
                        player.setResourcePack(finalUrl + "?timestamp=" + System.currentTimeMillis());
                        cancel();
                    } else {
                        cancel();
                    }
                }
            }.runTaskTimer(core.getPlugin(), 1L, 20L);
            return;
        }
        if (clickType != ClickType.LEFT)
            return;
        if (slot == 44) {
            Action.closePlayerInventory(core.getPlugin(), player);
        }
        else if (slot >= 27 && slot <= 31) {
            String server = "";
            switch (slot) {
                case (27) : {
                    server = "lobby";
                    break;
                }
                case (28) : {
                    server = "mainnet";
                    break;
                }
                case (29) : {
                    server = "rlcraft";
                    break;
                }
                case (30) : {
                    server = "stoneblock";
                    break;
                }
                case (31) : {
                    server ="morph";
                    break;
                }
            }
            if (server.isEmpty())
                return;
            if (server.equals(core.getDefaultConfig().getString("bungeecord.this"))) {
                Action.sendPlayerMessage(player, "<red>You already in " + server + " server.");
                return;
            }
            final String finalServer = server;
            final int[] count = {0};
            Action.closePlayerInventory(core.getPlugin(), player);
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (count[0] == 0) {
                        is_move.put(player, false);
                        final Title title = Title.title(Chat.minimessageComponent("<white>Preparing teleport..."), Chat.minimessageComponent("<red>Do not move"));
                        player.showTitle(title);
                        count[0]++;
                    } else if (count[0] < 6) {
                        if (is_move.get(player)) {
                            Action.sendPlayerMessage(player, "<red>Warp Cancelled");
                            is_move.remove(player);
                            cancel();
                        }
                        final Title title = Title.title(Chat.minimessageComponent("<white>Preparing teleport <red>" + (6 - count[0])), Chat.minimessageComponent("<red>Do not move"));
                        player.showTitle(title);
                        count[0]++;
                    } else {
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
