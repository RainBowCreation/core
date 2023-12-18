package net.rainbowcreation.api.utils;

import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;

/**
 * logger helper for easy debugging
 * Contain class and method when calling info
 */
public class RConsole {
    private ConsoleCommandSender console;

    public RConsole(ConsoleCommandSender console) {
        this.console = console;
    }

    /**
     *
     * @return logger
     */
    public ConsoleCommandSender get() {
        return this.console;
    }

    /**
     *
     * @param string
     */
    public void info(String string) {
        final StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        final String callingClass = stackTraceElements[2].getClassName();
        final String className = callingClass.substring(callingClass.lastIndexOf(".") + 1);
        final String callingMethod = stackTraceElements[2].getMethodName();
        console.sendMessage("(" + className + ") " + "[" + callingMethod + "] " + ChatColor.LIGHT_PURPLE+ string);
    }
}
