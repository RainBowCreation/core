package net.rainbowcreation.core.api.utils;

import java.util.Arrays;
import java.util.List;

public class Roman {
    private static final int[] VAL = {1000,900,500,400,100,90,50,40,10,9,5,4,1};
    private static final String[] ROMANS = {"m","cm","d","cd","c","xc","l","xl","x","ix","v","iv","i"};
    private static final List<String> ROMANS_LST = Arrays.asList(ROMANS);
    public static String S_serialize(int n) {
        if (n == 0)
            return "default";
        final StringBuilder F_rins = new StringBuilder();
        int len = VAL.length;
        for(int i=0;i<len;i++)
        {
            while(n >= VAL[i])
            {
                n-= VAL[i];
                F_rins.append(ROMANS[i]);
            }
        }
        return F_rins.toString();
    }

    public static int S_deseialize(String roman) {
        int n = 0;
        if (roman.equals("default"))
            return n;
        int len = roman.length();
        for(int i=0;i<len;i++) {
            String s = String.valueOf(roman.charAt(i));
            if (i > 0 && VAL[ROMANS_LST.indexOf(s)] > VAL[ROMANS_LST.indexOf(String.valueOf(roman.charAt(i-1)))])
                s = roman.substring(i++, i);
            n += VAL[ROMANS_LST.indexOf(s)];
        }
        return n;
    }
}
