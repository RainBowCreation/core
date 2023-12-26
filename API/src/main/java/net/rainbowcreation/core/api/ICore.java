package net.rainbowcreation.core.api;

import com.earth2me.essentials.Essentials;
import net.rainbowcreation.core.api.utils.Bungee;
import net.rainbowcreation.core.api.utils.Config;
import net.rainbowcreation.core.api.utils.Console;
import net.rainbowcreation.core.api.utils.GuiHolder;

public interface ICore {
    void register(ICore core);
    String getVersion();
    Console getConsole();
    Config getConfig(String name);
    Bungee getBungee();
    Essentials getEss();
    boolean isEssEnabled();
    GuiHolder getGuiHolder();
}
