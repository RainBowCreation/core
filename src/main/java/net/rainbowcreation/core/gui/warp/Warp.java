package net.rainbowcreation.core.gui.warp;

import com.earth2me.essentials.User;
import com.earth2me.essentials.Warps;
import net.rainbowcreation.core.Core;
import net.rainbowcreation.core.chat.Chat;
import net.rainbowcreation.core.chat.console.Console;
import net.rainbowcreation.core.eventmanager.inventoryclick.GuiClick;
import net.rainbowcreation.core.gui.Gui;
import net.rainbowcreation.core.utils.item.Item;
import net.rainbowcreation.core.utils.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class Warp {
    private static final Core plugin = Core.getInstance();
    private final FileConfiguration config = plugin.playerData.getConfig();
    public Item runSelf(Gui gui, Item item) {
        Player player = gui.getPlayer();
        User usr = plugin.ess.getUser(player);
        if  (usr.getHome("home") != null)
            item.lore("<white>RMB <gray>to warps <yellow>Home");
        if (Bukkit.getOfflinePlayer(player.getName()).getBedSpawnLocation() != null)
            item.lore("<white>Shift+RMB <gray>to warp <yellow>Bed");
        return item;
    }
    public Inventory run(Gui gui) {
        Inventory inv = gui.getInv();
        Player player = gui.getPlayer();
        User user = plugin.ess.getUser(player);
        Warps warps= plugin.ess.getWarps();
        List<String> warplist = new ArrayList<>(warps.getList());
        List<String > homelist = user.getHomes();
        String defaultLore = "<white>LMB <gray>to warp";
        String defaultDel = "<white>RMB <gray>to <red>DELETE";
        int i = 0;
        for (String warp:warplist) {
            int j = 0;
            if (i > 6)
                j = 2;
            if (i+j > 15)
                break;
            try {
                Location loc = warps.getWarp(warp);
                String xyz = "<gray>"+Math.round(loc.getX())+","+Math.round(loc.getY())+","+Math.round(loc.getZ());
                inv.setItem(i+j, new Item().material(Material.BEACON).displayName("<blue>"+warp).lore(defaultLore).lore(defaultDel).lore(xyz).get());
                i++;
            } catch (Exception ignore) {}
        }
        i = 0;
        for (String home:homelist) {
            if (i > 23)
                break;
            Location loc = user.getHome(home);
            String xyz = "<gray>"+Math.round(loc.getX())+","+Math.round(loc.getY())+","+Math.round(loc.getZ());
            Material material = Material.DIRT;
            switch (loc.getWorld().getName()) {
                case "world" -> material = Material.GRASS_BLOCK;
                case "world_nether" -> material = Material.NETHERRACK;
                case "world_the_end" -> material = Material.END_STONE;
            }
            inv.setItem(18+i, new Item().material(material).displayName("<green>"+home).lore(defaultLore).lore(defaultDel).lore(xyz).get());
            i++;
        }
        Location loc = player.getBedSpawnLocation();
        if (loc != null) {
            String xyz = "<gray>"+Math.round(loc.getX())+","+Math.round(loc.getY())+","+Math.round(loc.getZ());
            inv.setItem(24, new Item().material(Material.RED_BED).displayName("Bed").lore(defaultLore).lore(xyz).get());
        }
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
        ItemStack check = Gui.guiPart(6);
        Player player = (Player) event.getWhoClicked();
        User usr = plugin.ess.getUser(player);
        Warps warps = plugin.ess.getWarps();
        Location loc = null;
        int slot = event.getSlot();
        if (slot / 9  < 4 && slot - (slot/9)*9 < 7) {
            String name = item.getItemMeta().getDisplayName();
            try {
                if (slot / 9 < 2) { //warps
                    switch (clickType) {
                        case LEFT -> net.rainbowcreation.core.utils.player.warp.Warp.warp(usr, warps.getWarp(name),0);
                        case RIGHT -> {
                            if (!Permission.permission(player, "essentials.setwarp"))
                                return;
                            warps.removeWarp(name);
                            Chat.sendPlayerMessage(player,"<red>Del wap name:<white>%s <green>success!".formatted(name));
                            GuiClick.reload(event);
                        }
                    }
                } else if (slot == 24) { //bed
                    if (clickType == ClickType.LEFT) {
                        if (!Permission.permission(player, "essentials.home.bed"))
                            return;
                        net.rainbowcreation.core.utils.player.warp.Warp.warp(usr, player.getBedSpawnLocation(), 0);
                    }
                } else if (slot / 9 < 3) { //home
                    switch (clickType) {
                        case LEFT -> {
                            net.rainbowcreation.core.utils.player.warp.Warp.warp(usr, usr.getHome(name), 0);
                        }
                        case RIGHT -> {
                            if (!Permission.permission(player, "essentials.delhome"))
                                return;
                            usr.delHome(name);
                            Chat.sendPlayerMessage(player, "<red>Del home name:<white>%s <green>success!".formatted(name));
                            GuiClick.reload(event);
                        }
                    }
                } else if (slot == 32) {
                    if (clickType == ClickType.LEFT) {
                        if (!Permission.permission(player, "essentials.setwarp"))
                            return;
                        int i = plugin.ess.getWarps().getCount();
                        if (i >= 14) {
                            Chat.sendPlayerMessage(player, "<red>Error: Reach max warps amount");
                            return;
                        }
                        player.closeInventory();
                        BukkitRunnable runnable = runnable(event, "warp");
                        runnable.runTaskTimer(plugin, 0L, 20L);
                    }
                }else if (slot == 33) {
                    switch (clickType) {
                        case LEFT -> {
                            if (!Permission.permission(player, "essentials.sethome"))
                                return;
                            int i = usr.getHomes().size();
                            if (i >= 4 && !Permission.hasPermission(player, "essentials.sethome.multiple.vip")) {
                                Chat.sendPlayerMessage(player, "<red>Error: Reach max homes amount");
                                return;
                            }
                            if (i >= 6 && !Permission.isPlayerInGroup(player, "admin")) {
                                Chat.sendPlayerMessage(player, "<red>Error: Reach max homes amount");
                                return;
                            }
                            player.closeInventory();
                            BukkitRunnable runnable = runnable(event, "home");
                            runnable.runTaskTimer(plugin, 0L, 20L);
                        }
                        case RIGHT -> {
                            if (!Permission.permission(player, "essentials.sethome"))
                                return;
                            usr.setHome("home", player.getLocation());
                            Chat.sendPlayerMessage(player,"set home name:<white>home <green>success!");
                            GuiClick.reload(event);
                        }
                    }
                }
                return;
            } catch (Exception ignored) {}
        }
        if (item.equals(check)) { //click self icon
            if (clickType == ClickType.LEFT) //leftclick open that gui
                return;
            else {
                //if right-click or some else
                //todo
                if (clickType == ClickType.RIGHT)
                    loc = usr.getHome("home");
                else if (clickType == ClickType.SHIFT_RIGHT)
                    loc = player.getBedSpawnLocation();
                if (loc != null)
                    net.rainbowcreation.core.utils.player.warp.Warp.warp(usr, loc, 0);
            }
            return;
        }
        GuiClick.openGui(event); //redirect to other class if not in this handler
    }

    public BukkitRunnable runnable(InventoryClickEvent event, String mode) {
        Player player = (Player) event.getWhoClicked();
        User usr = plugin.ess.getUser(player);
        Chat.sendPlayerMessage(player,"Enter "+mode+" name:" );
        String prefix = "player."+player.getUniqueId()+".runnable";
        config.set(prefix+".case", mode);
        plugin.playerData.saveConfig();
        plugin.reloadConfig();
        BukkitRunnable runnable = new BukkitRunnable() {
            int round = 0;
            boolean success = true;
            @Override
            public void run() {
                plugin.reloadConfig();
                if (round >= 10) {
                    Chat.sendPlayerMessage(player, "<red> Error: Timeout!");
                    config.set(prefix+".case", "default");
                    config.set(prefix+".string", "");
                    plugin.playerData.saveConfig();
                    GuiClick.reload(event);
                    cancel();
                    return;
                }
                String name = config.getString(prefix+".string");
                if (!name.isEmpty()) {
                    switch (mode) {
                        case "home":
                            if (usr.getHomes().contains(name)) {
                                Chat.sendPlayerMessage(player,"<red>Error: duplicate name!");
                                success = false;
                            }
                            usr.setHome(name, player.getLocation());
                            break;
                        case "warp":
                            try {
                                if (plugin.ess.getWarps().getWarp(name) != null) {
                                    Chat.sendPlayerMessage(player,"<red>Error: duplicate name!");
                                    success = false;
                                }
                            } catch (Exception ignored) {}
                            try {
                                plugin.ess.getWarps().setWarp(name, player.getLocation());
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                            break;
                    }
                    Console.info(String.valueOf(success));
                    config.set(prefix+".case", "default");
                    config.set(prefix+".string", "");
                    plugin.playerData.saveConfig();
                    plugin.reloadConfig();
                    if (success)
                        Chat.sendPlayerMessage(player, "set "+mode+" name:<white>"+name+" <green>success!");
                    GuiClick.reload(event);
                    cancel();
                    return;
                }
                round++;
            }
        };
        plugin.playerData.saveConfig();
        plugin.reloadConfig();
        return runnable;
    }
}
