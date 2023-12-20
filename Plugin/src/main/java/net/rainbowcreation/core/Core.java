package net.rainbowcreation.core;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import net.rainbowcreation.core.api.INms;
import net.rainbowcreation.core.api.utils.Console;
import net.rainbowcreation.core.api.utils.Remap;
import net.rainbowcreation.core.api.utils.Str;
import net.rainbowcreation.core.event.Handler;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
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
    public INms P_nms_handler;
    public String P_version;

    public Console P_console;

    @Override
    public void onEnable() {
        ps_instance = this;
        P_console = new Console(this);
        p_protocolManager = ProtocolLibrary.getProtocolManager();
        final String F_package_name = ps_instance.getServer().getClass().getPackage().getName();
        // Get full package string of CraftServer.
        // org.bukkit.craftbukkit.version
        P_version = F_package_name.substring(F_package_name.lastIndexOf('.') + 1);
        // Get the last element of the package
        P_nms_handler = (INms) Remap.castInterface(INms.class, P_version, "NmsHandler"); // Set our handler
        if (P_nms_handler == null)
            return;
        P_console.info("Loading support for " + P_version);
        final PluginManager F_manager = Bukkit.getPluginManager();
        (new Handler()).register(F_manager, ps_instance);

        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(this, PacketType.Play.Server.TAB_COMPLETE) {
            @Override
            public void onPacketSending(PacketEvent event) {
                // Cancel the tab completion packet
                P_console.info("got");
                event.setCancelled(true);
            }
        });
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            P_console.info("command");
            final Player F_player = (Player) sender;
            P_nms_handler.sendMessage(F_player, Str.S_combineSplit(args));
        }
        return true;
    }
}
