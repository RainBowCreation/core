package net.rainbowcreation.core.api.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;

import java.util.logging.Logger;

@SuppressWarnings("unused")
public class Console {
    private Plugin plugin;
    private Logger logger;

    public Console(Plugin plugin) {
        this.plugin = plugin;
        logger = plugin.getLogger();
    }

    public void info(String string) {
        final StackTraceElement[] stack_trace_elements = Thread.currentThread().getStackTrace();
        final String calling_class = stack_trace_elements[2].getClassName();
        final String class_name = calling_class.substring(calling_class.lastIndexOf(".") + 1);
        final String calling_method = stack_trace_elements[2].getMethodName();
        //p_logger.info("(" + F_calling_method + ") " + "[" + F_calling_method + "] " + ChatColor.LIGHT_PURPLE + string);
        Bukkit.getConsoleSender().sendMessage("(" + calling_method + ") " + "[" + calling_method + "] " + ChatColor.LIGHT_PURPLE + string);
    }
}
