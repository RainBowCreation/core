package net.rainbowcreation.core;

import net.rainbowcreation.core.api.INms;
import net.rainbowcreation.core.api.utils.Remap;
import net.rainbowcreation.core.event.Handler;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Core extends JavaPlugin {
    private static Core ps_instance;

    public static Core PS_getInstance() {
        return ps_instance;
    }

    public INms P_nms_handler;
    public String P_version;

    @Override
    public void onEnable() {
        ps_instance = this;
        final String F_package_name = ps_instance.getServer().getClass().getPackage().getName();
        // Get full package string of CraftServer.
        // org.bukkit.craftbukkit.version
        P_version = F_package_name.substring(F_package_name.lastIndexOf('.') + 1);
        // Get the last element of the package

        P_nms_handler = (INms) Remap.cast(INms.class, P_version, "NMSHandler"); // Set our handler
        if (P_nms_handler == null)
            return;
        ps_instance.getLogger().info("Loading support for " + P_version);
        PluginManager manager = Bukkit.getPluginManager();
        (new Handler()).register(manager, ps_instance);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            final Player F_player = (Player) sender;
            P_nms_handler.sendMessage(F_player, ps_instance.combineSplit(args));
        }
        return true;
    }

    private String combineSplit(String[] split) {
        if (split.length == 0) {
            return "";
        } else if (split.length == 1) {
            return split[0];
        }
        final StringBuilder F_builder = new StringBuilder();
        for (String s : split) {
            F_builder.append(s).append(' ');
        }
        F_builder.setLength(F_builder.length() - 1);
        return F_builder.toString();
    }

}
