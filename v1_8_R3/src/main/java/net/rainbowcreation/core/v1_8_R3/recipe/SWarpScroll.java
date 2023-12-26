package net.rainbowcreation.core.v1_8_R3.recipe;

import net.rainbowcreation.core.api.ICore;
import net.rainbowcreation.core.v1_8_R3.Core;
import net.rainbowcreation.core.v1_8_R3.utils.Item;
import org.bukkit.Material;
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
        result = new Item().material(Material.EMPTY_MAP).displayName("<yellow>Warp Scroll").lore("<white>Teleport to selected friend").lore("<white>target player <red>MUST <white>be your friend and be in the same server").lore("<white>You can use /friend add <playername> to add player").lore("<gray>rainbowcreation.net").customModelData(1).amount(3).get();
        recipe = new ShapedRecipe(result);
        recipe.shape("010", "222", "313");
        recipe.setIngredient('0', Material.ENDER_PEARL);
        recipe.setIngredient('1', Material.INK_SACK, 5);
        recipe.setIngredient('2', Material.PAPER);
        recipe.setIngredient('3', Material.GOLD_NUGGET);
    }
}
