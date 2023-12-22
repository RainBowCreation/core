package net.rainbowcreation.core;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.*;
import com.comphenix.protocol.reflect.StructureModifier;
import com.comphenix.protocol.wrappers.EnumWrappers;
import net.rainbowcreation.core.api.INms;
import net.rainbowcreation.core.api.utils.Console;
import net.rainbowcreation.core.api.utils.Str;
import net.rainbowcreation.core.event.Handler;
import net.rainbowcreation.core.utils.Reference;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Core extends JavaPlugin {
    private static Core ps_instance;

    public static Core PS_getInstance() {
        return ps_instance;
    }

    private ProtocolManager p_protocolManager;
    public String P_version;

    public Console P_console;
    private final String[] pF_autocomplete = {"/login", "/register", "/warps"};

    @Override
    public void onEnable() {
        ps_instance = this;
        P_console = new Console(this);
        Str.header(Reference.NAME+":"+Reference.VERSION,P_console);
        p_protocolManager = ProtocolLibrary.getProtocolManager();
        final String F_package_name = ps_instance.getServer().getClass().getPackage().getName();
        // Get full package string of CraftServer.
        // org.bukkit.craftbukkit.version
        P_version = F_package_name.substring(F_package_name.lastIndexOf('.') + 1);
        // Get the last element of the package
        P_console.info("Loading support for " + P_version);
        final PluginManager F_manager = Bukkit.getPluginManager();
        (new Handler()).register(F_manager, ps_instance);

        p_protocolManager.addPacketListener(new PacketAdapter(ps_instance, PacketType.Play.Server.TAB_COMPLETE) {
            @Override
            public void onPacketSending(PacketEvent event) {
                event.getPacket().getStringArrays().write(0, pF_autocomplete);
            }
        });

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
