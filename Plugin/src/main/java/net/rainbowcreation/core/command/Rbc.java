package net.rainbowcreation.core.command;

import net.rainbowcreation.core.api.IGui;
import net.rainbowcreation.core.gui.Gui;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Rbc implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            final Player player = (Player) commandSender;
            player.openInventory(((IGui) Gui.instance.get("gui_main")).get());
            return true;
        }
        return false;
    }
}
