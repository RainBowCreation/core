package net.rainbowcreation.core.v1_8_R3;

import com.earth2me.essentials.Essentials;
import net.rainbowcreation.core.api.ICore;
import net.rainbowcreation.core.api.utils.Bungee;
import net.rainbowcreation.core.api.utils.Config;
import net.rainbowcreation.core.api.utils.Console;
import net.rainbowcreation.core.api.utils.GuiHolder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Map;

public class Core implements ICore {
    public static ICore instance;

    @Override
    public Plugin getPlugin() {
        return instance.getPlugin();
    }

    @Override
    public void register(ICore core) {
       instance = core;
    }

    @Override
    public String getVersion() {
        return instance.getVersion();
    }

    @Override
    public Console getConsole() {
        return instance.getConsole();
    }

    @Override
    public Config getConfig(String name) {
        return instance.getConfig(name);
    }

    @Override
    public Bungee getBungee() {
        return instance.getBungee();
    }

    @Override
    public Essentials getEss() {
        return instance.getEss();
    }

    @Override
    public boolean isEssEnabled() {
        return instance.isEssEnabled();
    }

    @Override
    public GuiHolder getGuiHolder() {
        return instance.getGuiHolder();
    }

    @Override
    public Map<Player, Boolean> getPlayerLog() {
        return instance.getPlayerLog();
    }
}
