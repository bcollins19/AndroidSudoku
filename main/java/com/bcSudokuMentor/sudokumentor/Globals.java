package com.bcSudokuMentor.sudokumentor;

import android.content.res.Resources;
import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Brett on 1/9/2015.
 */
public class Globals {
    public static final int HIGHLIGHT_COLOR = Color.GREEN;
    public static final int SHOW_COLOR = Color.YELLOW;
    public static final int POSSIBLE_COLOR = Color.BLUE;
    public static final int SQUARE_COLOR = Color.WHITE;
    public static final int TEXT_COLOR = Color.BLACK;
    public static final int DELETE_COLOR = Color.RED;

    public static final int BACKTRACKINGMAXCOUNT = 2;

    public static boolean useNeedNumber = true;
    public static boolean useSetClaim = true;
    public static boolean useOnlyNum = true;
    public static boolean useNakedNumbers = true;
    public static boolean useHiddenNumbers = true;
    public static boolean useXWing = true;
    public static boolean useColorChains = true;
    public static boolean useYWing = true;
    public static boolean useXCycle = true;

    public static String harderThanThisStrat = "OnlyNumber";

    public static boolean useBackTracking = true;

    public static int dpToPixel(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public static <T> List<T> listConcat(List<T> list1, List<T> list2) {
        List<T> result = new ArrayList<T>();
        result.addAll(list1);
        for (int i = 0; i < list2.size(); i++) {
            if (!result.contains(list2.get(i))) {
                result.add(list2.get(i));
            }
        }
        return result;
    }
}
