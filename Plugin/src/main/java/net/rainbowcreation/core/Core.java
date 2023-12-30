package net.rainbowcreation.core;

import com.earth2me.essentials.Essentials;
import net.rainbowcreation.core.api.ICore;
import net.rainbowcreation.core.api.utils.Bungee;
import net.rainbowcreation.core.api.utils.Config;
import net.rainbowcreation.core.api.utils.*;
import net.rainbowcreation.core.command.Command;
import net.rainbowcreation.core.event.Event;
import net.rainbowcreation.core.gui.Gui;
import net.rainbowcreation.core.message.BungeeListener;
import net.rainbowcreation.core.recipe.Shaped;
import net.rainbowcreation.core.recipe.Unshaped;
import net.rainbowcreation.core.utils.Reference;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.util.HashMap;
import java.util.Map;

public class Core extends JavaPlugin implements ICore {
    private static Core instance;

    public static Core getInstance() {
        return instance;
    }

    public Essentials ess;
    public Boolean ess_enabled = true;
    public String version;
    public Config config_gui;
    public Console console;
    private Bungee bungee;

    public Map<Player, Boolean> playerlog = new HashMap<>();
    private PluginMessageListener listener;

    @Override
    public void onEnable() {
        instance = this;
        console = new Console(this);

        // load header
        Str.header(Reference.NAME+":"+Reference.VERSION,console);
        final PluginManager manager = Bukkit.getPluginManager();

        // check for essential
        ess = (Essentials) instance.getServer().getPluginManager().getPlugin("Essentials");
        if (ess == null) {
            console.info("Please install Essentials for better performance"); //checker
            ess_enabled = false;
        }

        // get server version
        version = Str.getVersion(instance);
        console.info("Loading support for " + version);

        // config
        if (!getDataFolder().exists())
            getDataFolder().mkdir();
        saveDefaultConfig();
        config_gui = new Config(instance, "gui.yml", console);
        config_gui.saveConfig();

        listener = new BungeeListener();
        bungee = new Bungee(instance);

        register(instance);

        new Event().register();
        new Gui().register();
        new Command().register();
        new Shaped().register();
        new Unshaped().register();
    }

    @Override
    public void onDisable() {
        this.getServer().getMessenger().unregisterOutgoingPluginChannel(this);
        this.getServer().getMessenger().unregisterIncomingPluginChannel(this);
    }

    @Override
    public Bungee getBungee() {
        return bungee;
    }

    @Override
    public void register(ICore plugin) {
        final ICore core = (ICore) Remap.castInterface(ICore.class, version, "Core");
        if (core == null)
            return;
        core.register(plugin);
    }

    @Override
    public String getVersion() {
        return version;
    }

    @Override
    public Console getConsole() {
        return console;
    }

    @Override
    public Config getConfig(String name) {
        return null;
    }
    @Override
    public FileConfiguration getDefaultConfig() {
        return getPlugin().getConfig();
    }
    @Override
    public GuiHolder getGuiHolder() {
        return Gui.gui_holder;
    }

    @Override
    public Map<Player, Boolean> getPlayerLog() {
        return playerlog;
    }

    @Override
    public PluginMessageListener getMessageListener() {
        return listener;
    }

    @Override
    public Plugin getPlugin() {
        return (Plugin) instance;
    }
}
