package net.rainbowcreation.core.core.common.gui

import net.rainbowcreation.core.core.common.AbstractPlugin
import net.rainbowcreation.core.core.common.gui.menus.MainMenuGUI
import org.bukkit.entity.Player

/**
 * Manages the creation and opening of different GUIs for players.
 *
 * @param plugin The main instance of the plugin.
 */
class GUIManager(private val plugin: AbstractPlugin) {
    /**
     * Creates and opens the main menu GUI for a specific player.
     *
     * @param player The player for whom to open the main menu.
     */
    fun openMainMenu(player: Player) {
        val mainMenu = MainMenuGUI(plugin, player)
        mainMenu.open()
    }
}
