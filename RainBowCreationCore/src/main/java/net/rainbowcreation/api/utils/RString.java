package net.rainbowcreation.api.utils;

import java.util.logging.Logger;

/**
 * header
 */
public final class RString {
    private RString() {
        // void
    }

    /**
     *
     * @param name
     * @return
     */
    public static String genHeader(String name) {
        final StringBuilder header = new StringBuilder();
        final String c = "#";
        final int len = name.length();
        final int x = 40; // prefix
        final int y = 6; // suffix
        final int z = 86-x-y;
        final int tmp = z - len; // pre body
        for (int i = 0; i < x; i++) // do prefix
            header.append(c);
        for (int i = 0; i < tmp; i++) // do pre body
            header.append(c);
        header.append(name); // do body
        for (int i = 0; i < y; i++) // do suffix
            header.append(c);
        return header.toString();
    }

    /**
     *
     * @param args
     * @param logger
     */
    public void header(String args, Logger logger) {
        for (String string: Reference.HEADER) {
            logger.info(string);
        }
        logger.info(genHeader(args));
    }
}
