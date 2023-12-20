package net.rainbowcreation.core.api.utils;

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
}
