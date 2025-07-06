package net.rainbowcreation.core.core.common.gui.menus

import net.rainbowcreation.core.core.common.AbstractPlugin
import net.rainbowcreation.core.core.common.gui.GUI
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

/**
 * A concrete implementation of a main menu GUI.
 */
class MainMenuGUI(plugin: AbstractPlugin, player: Player) : GUI(plugin, player) {

    /**
     * Creates the inventory and adds items to it.
     */
    override fun createInventory() {
        // Create an inventory with 9 slots (1 row) and a title.
        inventory = plugin.server.createInventory(null, 9, "Main Menu")

        // Create an item to act as a "close" button.
        val closeItem = ItemStack(Material.BARRIER)
        val meta = closeItem.itemMeta
        meta?.setDisplayName("§cClose")
        closeItem.itemMeta = meta

        // Place the item in the middle slot.
        inventory.setItem(4, closeItem)
    }

    /**
     * Handles all click events within this specific GUI.
     */
    override fun handleClick(event: InventoryClickEvent) {
        // Prevent players from taking items out of the GUI.
        event.isCancelled = true

        // Check if the player clicked the close button.
        if (event.currentItem?.type == Material.BARRIER) {
            player.closeInventory()
        }
    }
}