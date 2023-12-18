package net.rainbowcreation.core;

import com.earth2me.essentials.Essentials;
import net.rainbowcreation.api.utils.RConsole;
import net.rainbowcreation.api.utils.RString;
import net.rainbowcreation.core.utils.Reference;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * core
 */
public class Core extends JavaPlugin {
    private static Core instance;
    public static RConsole console;
    public static Essentials ess;

    public static Core getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        console = new RConsole(Bukkit.getConsoleSender());
        final PluginManager manager = Bukkit.getPluginManager();
        RString.header(Reference.NAME + ":" + Reference.VERSION, console);
        ess = (Essentials) manager.getPlugin("Essentials");
        if (ess == null) {
            console.info("Essentials not Installed please Install and try again");
            Bukkit.getServer().shutdown();
        }
    }
}
