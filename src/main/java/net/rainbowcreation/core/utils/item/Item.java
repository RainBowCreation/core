package net.rainbowcreation.core.utils.item;

import net.kyori.adventure.text.Component;
import net.rainbowcreation.core.chat.Chat;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Item {
    private ItemStack itemStack;
    private Material material;
    private int n = 1;
    private Component displayName;
    private List<String> lore;
    private ItemMeta itemMeta;
    private int i = 0;

    public Item material(Material material) {
        this.material = material;
        return this;
    }
    public Material getMaterial() {
        return material;
    }
    public Item amount(int n) {
        this.n = n;
        return this;
    }
    public int getAmount() {
        return n;
    }
    public Item displayName(String mimnimessage) {
        displayName = Chat.minimessageComponent(mimnimessage);
        return this;
    }
    public Component getName() {
        return displayName;
    }
    public Item lore(List<String> loreListColored) {
        lore = loreListColored;
        return this;
    }
    public Item lore(String minimessage) {
        if (lore == null)
            lore  = new ArrayList<>();
        lore.add(Chat.minimessageColored(minimessage));
        return this;
    }
    public List<String> getLore() {
        return lore;
    }
    public Item customModelData(int modelNumber) {
        i = modelNumber;
        return this;
    }
    public int getCustomModelData() {
        return i;
    }
    public ItemStack get() {
        itemStack = new ItemStack(material, n);
        itemMeta = itemStack.getItemMeta();
        if (itemMeta == null)
            return itemStack;
        if (displayName != null)
            itemMeta.setDisplayName(Chat.componentColored(displayName));
        if (lore != null)
            itemMeta.setLore(lore);
        if (i != 0)
            itemMeta.setCustomModelData(i);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
    public ItemStack getrandomFirework() {
        this.material = Material.FIREWORK_ROCKET;
        FireworkMeta itemMeta = (FireworkMeta)this.itemStack.getItemMeta();
        assert itemMeta != null;
        itemMeta.setPower((int)(Math.random() * 2.0D + 3.0D));
        Color[] lst = {
                Color.red, Color.black, Color.blue, Color.cyan, Color.darkGray, Color.gray, Color.green, Color.lightGray, Color.magenta, Color.orange, Color.pink,
                Color.white, Color.yellow
        };
        int rand = (int)(Math.random() * lst.length);
        int rand2 = (int)(Math.random() * lst.length);
        FireworkEffect effect = FireworkEffect.builder().flicker(((int)(Math.random() * 2.0D) == 1)).withColor((Iterable<?>) lst[rand]).withFade((Iterable<?>) lst[rand2]).with(((int)(Math.random() * 2.0D) == 1) ? FireworkEffect.Type.BALL : FireworkEffect.Type.BALL_LARGE).trail(((int)(Math.random() * 2.0D) == 1)).build();
        itemMeta.addEffect(effect);
        this.itemStack.setItemMeta(itemMeta);
        return this.itemStack;
    }
}
