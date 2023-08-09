package net.rainbowcreation.rainbowcreationx.utils.math;

import net.rainbowcreation.rainbowcreationx.chat.Chat;

import java.util.ArrayList;
import java.util.List;

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
        int iSize = overrider.size();
        int jSize = overrider.get(0).size();

        for (int i = 0;i<iSize;i++) {
            List<Integer> tmp = overrider.get(i);
            for (int j = 0; j<jSize;j++)
                base.get(i).set(j, tmp.get(j));
        }
        return base;
    }
    public static List<String> minimessageColoredList(List<String> minimessageList) {
        List<String> lst = new ArrayList<>();
        for (String s:minimessageList)
            lst.add(Chat.minimessageColored(s));
        return lst;
    }
}
