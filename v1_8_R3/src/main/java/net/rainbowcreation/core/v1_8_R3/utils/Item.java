package net.rainbowcreation.core.v1_8_R3.utils;

import net.kyori.adventure.text.Component;
import net.rainbowcreation.core.api.IItem;
import net.rainbowcreation.core.api.utils.Chat;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class Item implements IItem {
    private ItemStack itemStack;
    private Material material;
    private int n = 1;
    private Component displayName;
    private List<String> lore;
    private ItemMeta itemMeta;
    private int color = 0;
    private int i = 0;
    private static final Color[] COLOR_LIST = {
            Color.red, Color.black, Color.blue, Color.cyan, Color.darkGray, Color.gray, Color.green, Color.lightGray, Color.magenta, Color.orange, Color.pink,
            Color.white, Color.yellow
    };

    @Override
    public Item material(Material material) {
        this.material = material;
        return this;
    }

    public Item color(int color) {
        this.color = color;
        return this;
    }

    @Override
    public Material getMaterial() {
        return material;
    }

    @Override
    public Item amount(int n) {
        this.n = n;
        return this;
    }

    @Override
    public int getAmount() {
        return n;
    }

    @Override
    public Item displayName(String mimnimessage) {
        displayName = Chat.minimessageComponent(mimnimessage);
        return this;
    }

    @Override
    public Component getName() {
        return displayName;
    }

    @Override
    public Item lore(List<String> loreListColored) {
        lore = loreListColored;
        return this;
    }

    @Override
    public Item lore(String minimessage) {
        if (lore == null)
            lore  = new ArrayList<>();
        lore.add(Chat.minimessageColored(minimessage));
        return this;
    }

    @Override
    public List<String> getLore() {
        return lore;
    }

    @Override
    public Item customModelData(int modelNumber) {
        i = modelNumber;
        return this;
    }

    @Override
    public int getCustomModelData() {
        return i;
    }

    @Override
    public ItemStack get() {
        itemStack = new ItemStack(material, n, (short) color);
        itemMeta = itemStack.getItemMeta();
        if (itemMeta == null)
            return itemStack;
        if (displayName != null)
            itemMeta.setDisplayName(Chat.componentColored(displayName));
        if (lore != null)
            itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        if (i != 0) {
            net.minecraft.server.v1_8_R3.ItemStack data = CraftItemStack.asNMSCopy(itemStack);
            data.getTag().setInt("CustomModelData", i);
            itemStack = CraftItemStack.asBukkitCopy(data);
        }
        return itemStack;
    }

    @Override
    public ItemStack getrandomFirework() {
        material = Material.FIREWORK;
        final FireworkMeta F_itemMeta = (FireworkMeta)itemStack.getItemMeta();
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
        itemStack.setItemMeta(F_itemMeta);
        return itemStack;
    }
}
