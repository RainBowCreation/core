package net.rainbowcreation.core.core.common.gui

import net.rainbowcreation.core.core.common.AbstractPlugin
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.inventory.Inventory

/**
 * An abstract base class for creating interactive inventory-based GUIs.
 *
 * @property plugin The main instance of the plugin (LegacyPlugin or ModernPlugin).
 * @property player The player who is viewing this GUI.
 */
abstract class GUI(
    protected val plugin: AbstractPlugin,
    protected val player: Player
) : Listener {

    protected lateinit var inventory: Inventory

    init {
        // When a new GUI is created, register it as a listener for its own events.
        plugin.server.pluginManager.registerEvents(this, plugin)
    }

    /**
     * Creates the inventory and its contents. Must be implemented by subclasses.
     */
    protected abstract fun createInventory()

    /**
     * Handles clicks within this specific GUI. Must be implemented by subclasses.
     *
     * @param event The InventoryClickEvent.
     */
    protected abstract fun handleClick(event: InventoryClickEvent)

    /**
     * Opens the inventory for the player.
     */
    fun open() {
        createInventory()
        player.openInventory(inventory)
    }

    /**
     * The main click handler that passes control to the specific implementation.
     */
    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        if (event.inventory != inventory) return
        handleClick(event)
    }

    /**
     * Automatically unregisters the listener when the GUI is closed to prevent memory leaks.
     */
    @EventHandler
    fun onInventoryClose(event: InventoryCloseEvent) {
        if (event.inventory == inventory) {
            InventoryCloseEvent.getHandlerList().unregister(this)
        }
    }
}