package net.rainbowcreation.core.v1_12_R1.utils;

import net.kyori.adventure.text.Component;
import net.rainbowcreation.core.api.IItem;
import net.rainbowcreation.core.api.utils.Chat;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
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

    private static final org.bukkit.Color[] BUKKIT_COLOR_LIST = {
            org.bukkit.Color.RED, org.bukkit.Color.BLACK, org.bukkit.Color.BLUE, org.bukkit.Color.AQUA, org.bukkit.Color.GRAY, org.bukkit.Color.FUCHSIA, org.bukkit.Color.GREEN, org.bukkit.Color.LIME, org.bukkit.Color.MAROON, org.bukkit.Color.NAVY, org.bukkit.Color.OLIVE,
            org.bukkit.Color.ORANGE, org.bukkit.Color.PURPLE, org.bukkit.Color.SILVER, org.bukkit.Color.TEAL, org.bukkit.Color.WHITE, org.bukkit.Color.YELLOW
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
            net.minecraft.server.v1_12_R1.ItemStack data = CraftItemStack.asNMSCopy(itemStack);
            data.getTag().setInt("CustomModelData", i);
            itemStack = CraftItemStack.asBukkitCopy(data);
        }
        return itemStack;
    }

    @Override
    public ItemStack getrandomFirework() {
        material = Material.FIREWORK;
        itemStack = new ItemStack(material, 4);
        final FireworkMeta itemMeta = (FireworkMeta) itemStack.getItemMeta();
        assert itemMeta != null;
        itemMeta.setPower((int) (Math.random() * 2.0D + 3.0D));

        int rand = (int) (Math.random() * BUKKIT_COLOR_LIST.length);
        int rand2 = (int) (Math.random() * BUKKIT_COLOR_LIST.length);

        final FireworkEffect.Builder effect = FireworkEffect.builder();
        effect.flicker(((int) (Math.random() * 2.0D) == 1)).withColor(BUKKIT_COLOR_LIST[rand]);
        effect.withFade(BUKKIT_COLOR_LIST[rand2]);
        effect.withFade();
        effect.with(((int) (Math.random() * 2.0D) == 1) ? FireworkEffect.Type.BALL : FireworkEffect.Type.BALL_LARGE);
        effect.trail(((int) (Math.random() * 2.0D) == 1));

        itemMeta.addEffect(effect.build());
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}
