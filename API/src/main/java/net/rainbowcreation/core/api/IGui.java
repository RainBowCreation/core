package net.rainbowcreation.core.api;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public interface IGui {
    Inventory get();
    Inventory getDynamic(Player player);
    void onClick(InventoryClickEvent event);
    boolean isDynamic();
}