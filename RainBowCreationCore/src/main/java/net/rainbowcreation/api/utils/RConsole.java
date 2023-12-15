package net.rainbowcreation.api.utils;

import java.util.logging.Logger;

/**
 * logger helper for easy debugging
 * Contain class and method when calling info
 */
public class RConsole {
    private Logger logger;

    public RConsole(Logger logger) {
        this.logger = logger;
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
        logger.info("(" + className + ") " + "[" + callingMethod + "] " + string);
    }
}
