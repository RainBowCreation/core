package net.rainbowcreation.core.api.utils;

import java.util.logging.Logger;

public class Str {
    public static String S_combineSplit(String[] split) {
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

    public static void header(String args, Logger logger) {
        for (String string: Reference.HEADER) {
            logger.info(string);
        }
        logger.info(genHeader(args));
    }

    public static void header(String args, Console logger) {
        for (String string: Reference.HEADER) {
            logger.info(string);
        }
        logger.info(genHeader(args));
    }
}
