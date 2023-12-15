package net.rainbowcreation.api.utils;

import net.kyori.adventure.text.Component;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * item helper
 */
public class RItem {
    private ItemStack itemStack;
    private Material material;
    private int n = 1;
    private Component displayName;
    private List<String> lore;
    private ItemMeta itemMeta;
    private int i = 0;
    private Color[] lst = {Color.red, Color.black, Color.blue, Color.cyan, Color.darkGray, Color.gray, Color.green, Color.lightGray, Color.magenta, Color.orange, Color.pink, Color.white, Color.yellow};

    /**
     * set material
     * @param material
     * @return RItem
     */
    public RItem material(Material material) {
        this.material = material;
        return this;
    }

    /**
     * getter
     * @return Material
     */
    public Material getMaterial() {
        return material;
    }

    /**
     * set amount
     * @param n
     * @return RItem
     */
    public RItem amount(int n) {
        this.n = n;
        return this;
    }

    /**
     * getter
     * @return int
     */
    public int getAmount() {
        return n;
    }

    /**
     * setter
     * @param mimnimessage
     * @return RITem
     */
    public RItem displayName(String mimnimessage) {
        displayName = RChat.minimessageComponent(mimnimessage);
        return this;
    }

    /**
     * getter
     * @return Displayname Component
     */
    public Component getName() {
        return displayName;
    }

    /**
     * setter
     * @param loreListColored
     * @return RItem
     */
    public RItem lore(List<String> loreListColored) {
        lore = loreListColored;
        return this;
    }

    /**
     * setter
     * @param minimessage
     * @return RItem
     */
    public RItem lore(String minimessage) {
        if (lore == null)
            lore = new ArrayList<>();
        lore.add(RChat.minimessageColored(minimessage));
        return this;
    }

    /**
     * getter
     * @return List of lore
     */
    public List<String> getLore() {
        return lore;
    }

    /**
     * setter
     * @param modelNumber
     * @return RITtm
     */
    public RItem customModelData(int modelNumber) {
        i = modelNumber;
        return this;
    }

    /**
     * getter
     * @return custommodeldata as int
     */
    public int getCustomModelData() {
        return i;
    }

    /**
     * create item should be call last after set all needed
     * @return ItemStack
     */
    public ItemStack get() {
        itemStack = new ItemStack(material, n);
        itemMeta = itemStack.getItemMeta();
        if (itemMeta == null)
            return itemStack;
        if (displayName != null)
            itemMeta.setDisplayName(RChat.componentColored(displayName));
        if (lore != null)
            itemMeta.setLore(lore);
        if (i != 0)
            itemMeta.setCustomModelData(i);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    /**
     * random firwork stat
     * @return ItemStack
     */
    public ItemStack getrandomFirework() {
        this.material = Material.FIREWORK_ROCKET;
        final FireworkMeta itemMeta = (FireworkMeta)this.itemStack.getItemMeta();
        assert itemMeta != null;
        itemMeta.setPower((int) (rand2D() + 3.0D));
        final FireworkEffect.Builder effect = FireworkEffect.builder();
        effect.flicker((rand2D() == 1) ? true : false);
        effect.withColor((Iterable<?>) lst[rand(lst.length)]);
        effect.withFade((Iterable<?>) lst[rand(lst.length)]);
        effect.with((rand2D() == 1) ? FireworkEffect.Type.BALL : FireworkEffect.Type.BALL_LARGE);
        effect.trail((rand2D() == 1) ? true : false);
        itemMeta.addEffect(effect.build());
        this.itemStack.setItemMeta(itemMeta);
        return this.itemStack;
    }

    private int rand2D() {
        return (int)(Math.random() * 2.0D);
    }

    private int rand(int len) {
        return (int)(Math.random() * len);
    }
}
