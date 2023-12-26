package net.rainbowcreation.core.v1_20_R1;

import com.earth2me.essentials.Essentials;
import net.rainbowcreation.core.api.ICore;
import net.rainbowcreation.core.api.utils.Bungee;
import net.rainbowcreation.core.api.utils.Config;
import net.rainbowcreation.core.api.utils.Console;
import net.rainbowcreation.core.api.utils.GuiHolder;
import org.bukkit.plugin.Plugin;

public class Core implements ICore {
    public static ICore instance;
    public static Plugin plugin;

    @Override
    public void register(ICore core) {
       instance = core;
       plugin = (Plugin) instance;
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
}
