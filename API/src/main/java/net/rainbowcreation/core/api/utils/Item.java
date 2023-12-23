package net.rainbowcreation.core.api.utils;

import net.kyori.adventure.text.Component;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class Item {
    private ItemStack p_itemStack;
    private Material p_material;
    private int p_n = 1;
    private Component p_displayName;
    private List<String> p_lore;
    private ItemMeta p_itemMeta;
    private int p_i = 0;
    private static final Color[] COLOR_LIST = {
            Color.red, Color.black, Color.blue, Color.cyan, Color.darkGray, Color.gray, Color.green, Color.lightGray, Color.magenta, Color.orange, Color.pink,
            Color.white, Color.yellow
    };

    public Item material(Material material) {
        p_material = material;
        return this;
    }
    public Material getMaterial() {
        return p_material;
    }
    public Item amount(int n) {
        p_n = n;
        return this;
    }
    public int getAmount() {
        return p_n;
    }
    public Item displayName(String mimnimessage) {
        p_displayName = Chat.S_minimessageComponent(mimnimessage);
        return this;
    }
    public Component getName() {
        return p_displayName;
    }
    public Item lore(List<String> loreListColored) {
        p_lore = loreListColored;
        return this;
    }
    public Item lore(String minimessage) {
        if (p_lore == null)
            p_lore  = new ArrayList<>();
        p_lore.add(Chat.S_minimessageColored(minimessage));
        return this;
    }
    public List<String> getLore() {
        return p_lore;
    }
    public Item customModelData(int modelNumber) {
        p_i = modelNumber;
        return this;
    }
    public int getCustomModelData() {
        return p_i;
    }
    public ItemStack get() {
        p_itemStack = new ItemStack(p_material, p_n);
        p_itemMeta = p_itemStack.getItemMeta();
        if (p_itemMeta == null)
            return p_itemStack;
        if (p_displayName != null)
            p_itemMeta.setDisplayName(Chat.S_componentColored(p_displayName));
        if (p_lore != null)
            p_itemMeta.setLore(p_lore);
        /*
        if (p_i != 0)
            p_itemMeta.setCustomModelData(p_i);

         */
        p_itemStack.setItemMeta(p_itemMeta);
        return p_itemStack;
    }
    public ItemStack getrandomFirework() {
        p_material = Material.FIREWORK_ROCKET;
        final FireworkMeta F_itemMeta = (FireworkMeta)p_itemStack.getItemMeta();
        assert F_itemMeta != null;
        F_itemMeta.setPower((int)(Math.random() * 2.0D + 3.0D));
        int rand = (int)(Math.random() * COLOR_LIST.length);
        int rand2 = (int)(Math.random() * COLOR_LIST.length);
        final FireworkEffect.Builder F_effect = FireworkEffect.builder();
        F_effect.flicker(((int)(Math.random() * 2.0D) == 1)).withColor((Iterable<?>) COLOR_LIST[rand]);
        F_effect.withFade((Iterable<?>) COLOR_LIST[rand2]);
        F_effect.with(((int)(Math.random() * 2.0D) == 1) ? FireworkEffect.Type.BALL : FireworkEffect.Type.BALL_LARGE);
        F_effect.trail(((int)(Math.random() * 2.0D) == 1));
        F_itemMeta.addEffect(F_effect.build());
        p_itemStack.setItemMeta(F_itemMeta);
        return p_itemStack;
    }
}
