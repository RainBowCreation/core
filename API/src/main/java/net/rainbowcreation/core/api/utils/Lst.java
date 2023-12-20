package net.rainbowcreation.core.api.utils;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class Lst {
    public static int[] S_toIJ(int slot) {
        int[] result = new int[2];
        result[0] = slot / 9;
        result[1] = slot - result[0] * 9;
        return result;
    }
    public static int S_toSlot(int i, int j) {
        return 9*i+j;
    }
    public static List<List<Integer>> S_overrideList(List<List<Integer>> base, List<List<Integer>> overrider) {
        final int F_iSize = overrider.size();
        final int F_jSize = overrider.get(0).size();

        for (int i = 0;i<F_iSize;i++) {
            final List<Integer> F_tmp = overrider.get(i);
            for (int j = 0; j<F_jSize;j++)
                base.get(i).set(j, F_tmp.get(j));
        }
        return base;
    }
    public static List<String> S_minimessageColoredList(List<String> minimessageList) {
        final List<String> F_lst = new ArrayList<>();
        for (String s:minimessageList)
            F_lst.add(Chat.S_minimessageColored(s));
        return F_lst;
    }
}
