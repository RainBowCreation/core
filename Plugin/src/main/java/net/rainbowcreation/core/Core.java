package net.rainbowcreation.core;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import net.rainbowcreation.core.api.config.Config;
import net.rainbowcreation.core.api.utils.Console;
import net.rainbowcreation.core.api.utils.Gui;
import net.rainbowcreation.core.api.utils.Str;
import net.rainbowcreation.core.event.Handler;
import net.rainbowcreation.core.utils.Reference;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Core extends JavaPlugin {
    private static Core ps_instance;

    public static Core PS_getInstance() {
        return ps_instance;
    }

    private ProtocolManager p_protocolManager;
    public String P_version;
    public Config P_config_gui;
    public Console P_console;

    @Override
    public void onEnable() {
        ps_instance = this;
        P_console = new Console(this);
        Str.header(Reference.NAME+":"+Reference.VERSION,P_console);
        p_protocolManager = ProtocolLibrary.getProtocolManager();
        final String F_package_name = ps_instance.getServer().getClass().getPackage().getName();
        P_version = F_package_name.substring(F_package_name.lastIndexOf('.') + 1);
        P_console.info("Loading support for " + P_version);
        if (!getDataFolder().exists())
            getDataFolder().mkdir();
        saveDefaultConfig();
        P_config_gui = new Config(ps_instance, "gui.yml", P_console);
        P_config_gui.saveConfig();
        final Gui F_gui = new Gui(ps_instance, P_config_gui.getConfig());
        final Handler F_handler = new Handler();
        final PluginManager F_manager = Bukkit.getPluginManager();
        F_handler.register(F_manager, ps_instance);
        /*
        p_protocolManager.addPacketListener(new PacketAdapter(ps_instance) {
            @Override
            public void onPacketSending(PacketEvent event) {
                // Intercept outgoing packets (client to server)
            }

            @Override
            public void onPacketReceiving(PacketEvent event) {
                // Intercept incoming packets (server to client)
                Player player = event.getPlayer();
                PacketContainer packet = event.getPacket();
                P_console.info(packet.getStrings().read(0));
                // Check if it's a Handshake packet (status change in 1.8)
            }
        });

         */
    }
}
