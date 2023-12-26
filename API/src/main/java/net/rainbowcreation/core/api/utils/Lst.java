package net.rainbowcreation.core.api.utils;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class Lst {
    public static int[] toIJ(int slot) {
        int[] result = new int[2];
        result[0] = slot / 9;
        result[1] = slot - result[0] * 9;
        return result;
    }
    public static int toSlot(int i, int j) {
        return 9*i+j;
    }
    public static List<List<Integer>> overrideList(List<List<Integer>> base, List<List<Integer>> overrider) {
        final int iSize = overrider.size();
        final int jSize = overrider.get(0).size();

        for (int i = 0;i<iSize;i++) {
            final List<Integer> F_tmp = overrider.get(i);
            for (int j = 0; j<jSize;j++)
                base.get(i).set(j, F_tmp.get(j));
        }
        return base;
    }
    public static List<String> minimessageColoredList(List<String> minimessageList) {
        final List<String> lst = new ArrayList<>();
        for (String s:minimessageList)
            lst.add(Chat.minimessageColored(s));
        return lst;
    }
}
