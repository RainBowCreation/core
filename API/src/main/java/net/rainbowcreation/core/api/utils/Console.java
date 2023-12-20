package net.rainbowcreation.core.api.utils;

import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;

import java.util.logging.Logger;

@SuppressWarnings("unused")
public class Console {
    private final Plugin pF_plugin;
    private Logger p_logger;

    public Console(Plugin plugin) {
        pF_plugin = plugin;
        p_logger = plugin.getLogger();
    }

    public void info(String string) {
        final StackTraceElement[] F_stack_trace_elements = Thread.currentThread().getStackTrace();
        final String F_calling_class = F_stack_trace_elements[2].getClassName();
        final String F_class_name = F_calling_class.substring(F_calling_class.lastIndexOf(".") + 1);
        final String F_calling_method = F_stack_trace_elements[2].getMethodName();
        p_logger.info(Chat.S_legacyColored("(" + F_calling_method + ") " + "[" + F_calling_method + "] " + ChatColor.LIGHT_PURPLE + string));
    }
}
