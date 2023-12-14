package net.rainbowcreation.core;

import com.earth2me.essentials.Essentials;
import net.rainbowcreation.api.API;
import net.rainbowcreation.api.utils.Logger;
import net.rainbowcreation.core.utils.Reference;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import javax.swing.plaf.PanelUI;


public class Core extends JavaPlugin {
    private static Core instance;
    public Essentials ess;
    public static Logger logger;

    public static Core getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        logger = new Logger();
        PluginManager manager = Bukkit.getPluginManager();
        API.rString.genHeader(Reference.NAME + ":" + Reference.VERSION);
        ess = (Essentials) manager.getPlugin("Essentials");
        if (ess == null) {
            API.logger.info("Essentials not Installed please Install and try again");
        }
    }
}
