package net.rainbowcreation.core.api;

import com.earth2me.essentials.Essentials;
import net.rainbowcreation.core.api.utils.Bungee;
import net.rainbowcreation.core.api.utils.Config;
import net.rainbowcreation.core.api.utils.Console;
import net.rainbowcreation.core.api.utils.GuiHolder;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.util.Map;

public interface ICore {
    Plugin getPlugin();
    void register(ICore core);
    String getVersion();
    Console getConsole();
    Config getConfig(String name);
    Bungee getBungee();
    Essentials getEss();
    boolean isEssEnabled();
    GuiHolder getGuiHolder();
    Map<Player, Boolean> getPlayerLog();
    PluginMessageListener getMessageListener();
    FileConfiguration getDefaultConfig();
}
