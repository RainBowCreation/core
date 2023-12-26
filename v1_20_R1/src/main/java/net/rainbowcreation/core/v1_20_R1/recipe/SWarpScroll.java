package net.rainbowcreation.core.v1_20_R1.recipe;

import de.simonsator.partyandfriends.spigot.api.exceptions.FriendsAPIBridgeNotInstalledException;
import de.simonsator.partyandfriends.spigot.api.pafplayers.PAFPlayer;
import de.simonsator.partyandfriends.spigot.api.pafplayers.PAFPlayerManager;
import net.rainbowcreation.core.api.ICore;
import net.rainbowcreation.core.api.utils.Chat;
import net.rainbowcreation.core.api.utils.Item;
import net.rainbowcreation.core.v1_20_R1.Core;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

public class SWarpScroll {
    String namespace;
    ItemStack result;
    ICore core;
    ShapedRecipe recipe;

    public SWarpScroll() {
        core = Core.instance;
        namespace = "warp_scroll";
        result = new Item().material(Material.MAP).displayName("<yellow>Warp Scroll").lore("<white>Teleport to selected friend").lore("<white>target player <red>MUST <white>online in the same server").lore("<white>You can use /friend add <playername> to add player").lore("<gray>wiki.rainbowcreation.net/item/warp_scroll").customModelData(1).amount(3).get();
        recipe = new ShapedRecipe(new NamespacedKey(core.getPlugin(), namespace), result);
        recipe.shape("010", "222", "313");
        recipe.setIngredient('0', Material.ENDER_PEARL);
        recipe.setIngredient('1', Material.PURPLE_DYE);
        recipe.setIngredient('2', Material.PAPER);
        recipe.setIngredient('3', Material.GOLD_NUGGET);
    }

    public static void onUse(PlayerInteractEvent event) {
        event.setCancelled(true);
        final Player player = event.getPlayer();
        player.sendMessage("<red>Sorry this item is under development TT");
        /*
        ItemStack item = event.getItem();
        if (item.getAmount() > 1) {
            item.setAmount(item.getAmount() - 1);
        } else {
            player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
        }
        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_STARE, 1.0f, 1.0f);
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
            if (Bukkit.getPlayer(friend.getName()).isOnline())
                event.getPlayer().sendMessage("| " + friend.getName());
        }
        player.sendMessage("Please type target friend");
         */
        // todo make checker runnable for teleport
    }
}
