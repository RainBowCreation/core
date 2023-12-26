package net.rainbowcreation.core.v1_8_R3.recipe;

import de.simonsator.partyandfriends.spigot.api.exceptions.FriendsAPIBridgeNotInstalledException;
import de.simonsator.partyandfriends.spigot.api.pafplayers.PAFPlayer;
import de.simonsator.partyandfriends.spigot.api.pafplayers.PAFPlayerManager;
import net.rainbowcreation.core.api.ICore;
import net.rainbowcreation.core.api.utils.Chat;
import net.rainbowcreation.core.v1_8_R3.Core;
import net.rainbowcreation.core.v1_8_R3.utils.Item;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.scheduler.BukkitRunnable;

public class SWarpScroll {
    String namespace;
    ItemStack result;
    ICore core;
    ShapedRecipe recipe;

    public SWarpScroll() {
        core = Core.instance;
        namespace = "warp_scroll";
        result = new Item().material(Material.EMPTY_MAP).displayName("<yellow>Warp Scroll").lore("<white>Teleport to selected friend").lore("<white>target player <red>MUST <white>online in the same server").lore("<white>You can use /friend add <playername> to add player").lore("<gray>wiki.rainbowcreation.net/item/warp_scroll").customModelData(1).amount(3).get();
        recipe = new ShapedRecipe(result);
        recipe.shape("010", "222", "313");
        recipe.setIngredient('0', Material.ENDER_PEARL);
        recipe.setIngredient('1', Material.INK_SACK, 5);
        recipe.setIngredient('2', Material.PAPER);
        recipe.setIngredient('3', Material.GOLD_NUGGET);
    }

    public static void onUse(PlayerInteractEvent event) {
        event.setCancelled(true);
        final Player player = event.getPlayer();
        player.sendMessage(Chat.minimessageColored("<red>Sorry this item is under development TT"));
        return;
        /*
        ItemStack item = event.getItem();
        if (item.getAmount() > 1) {
            item.setAmount(item.getAmount() - 1);
        } else {
            player.getInventory().setItemInHand(new ItemStack(Material.AIR));
        }
        player.getWorld().playSound(player.getLocation(), Sound.ENDERMAN_STARE, 1.0f, 1.0f);
        PAFPlayer pafPlayer = PAFPlayerManager.getInstance().getPlayer(player.getUniqueId());
        player.sendMessage(Chat.minimessageColored("<white>[<yellow>Warp Scroll<white>] your online friends."));
        if (pafPlayer.getFriends().isEmpty()) {
            player.sendMessage(Chat.minimessageColored("Oh! im sorry, you have no friends"));
            player.sendMessage(Chat.minimessageColored("use <green>/friend add (playername) to add new friend"));
            return;
        }
        try {
            if (pafPlayer.getOnlineFriendsCount() == 0) {
                player.sendMessage(Chat.minimessageColored("You have no online friends"));
                return;
            }
        } catch (FriendsAPIBridgeNotInstalledException e) {
            throw new RuntimeException(e);
        }
        for (PAFPlayer friend : pafPlayer.getFriends()) {
            if (((Player) friend).isOnline())
                event.getPlayer().sendMessage("| " + friend.getName());
        }
        player.sendMessage("Please type target friend");

         */

        // todo make checker runnable for teleport
    }
}
