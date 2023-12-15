package net.rainbowcreation.api.utils;

import java.util.Arrays;
import java.util.List;

/**
 * roman str
 */
public final class RRoman {
    private RRoman() {
        // void
    }

    private static final int[] val = {1000,900,500,400,100,90,50,40,10,9,5,4,1};
    private static final String[] romans = {"m","cm","d","cd","c","xc","l","xl","x","ix","v","iv","i"};

    /**
     *
     * @param n
     * @return
     */
    public static String serialize(int n) {
        if (n == 0)
            return "default";
        final StringBuilder rins = new StringBuilder();
        final int len = val.length;
        for(int i=0;i<len;i++) {
            while(n >= val[i]) {
                n-= val[i];
                rins.append(romans[i]);
            }
        }
        return rins.toString();
    }

    /**
     *
     * @param roman
     * @return
     */
    public static int deseialize(String roman) {
        int n = 0;
        if (roman.equals("default"))
            return n;
        final List<String> rom = Arrays.asList(romans);

        final int len = roman.length();
        for(int i=0;i<len;i++) {
            String s = String.valueOf(roman.charAt(i));
            if (i > 0 && val[rom.indexOf(s)] > val[rom.indexOf(String.valueOf(roman.charAt(i-1)))])
                s = roman.substring(i++, i);
            n += val[rom.indexOf(s)];
        }
        return n;
    }
}
