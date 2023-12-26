package net.rainbowcreation.core.api;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public interface IItem {
    IItem material(Material material);
    Material getMaterial();
    IItem amount(int n);
    int getAmount();
    IItem displayName(String mimnimessage);
    Component getName();
    IItem lore(List<String> loreListColored);
    IItem lore(String minimessage);
    List<String> getLore();
    IItem customModelData(int modelNumber);
    int getCustomModelData();
    ItemStack get();
    ItemStack getrandomFirework();
}
