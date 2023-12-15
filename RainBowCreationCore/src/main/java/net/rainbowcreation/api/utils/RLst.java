package net.rainbowcreation.api.utils;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * list init
 */
public final class RLst {
    private RLst() {
        // void
    }

    /**
     *
     * @param slot
     * @return
     */
    @Contract(pure = true)
    public static int @NotNull [] toIJ(int slot) {
        final int[] result = new int[2];
        result[0] = slot / 9;
        result[1] = slot - result[0] * 9;
        return result;
    }

    /**
     *
     * @param i
     * @param j
     * @return
     */
    public static int toSlot(int i, int j) {
        return 9*i+j;
    }

    /**
     *
     * @param base
     * @param overrider
     * @return
     */
    @Contract("_, _ -> param1")
    public static List<List<Integer>> overrideList(List<List<Integer>> base, @NotNull List<List<Integer>> overrider) {
        final int iSize = overrider.size();
        final int jSize = overrider.get(0).size();

        for (int i = 0;i<iSize;i++) {
            final List<Integer> tmp = overrider.get(i);
            for (int j = 0; j<jSize;j++)
                base.get(i).set(j, tmp.get(j));
        }
        return base;
    }

    /**
     *
     * @param minimessageList
     * @return
     */
    public static @NotNull List<String> minimessageColoredList(@NotNull List<String> minimessageList) {
        final List<String> lst = new ArrayList<>();
        for (String s:minimessageList)
            lst.add(RChat.minimessageColored(s));
        return lst;
    }
}
