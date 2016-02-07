package com.bcSudokuMentor.sudokumentor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Brett on 8/23/2015.
 */
public class HiddenNumbers {

    public static boolean hiddenNumbers(Puzzle puz) {
        boolean result = false;
        for (int i = 2; i < 5; i++) {
            for (int j = 0; j < 27; j++) {
                result = hiddenTest(puz, puz.getSet(j), i);
                if (result) {
                    return true;
                }
            }
        }
        return false;
    }


    public static boolean hiddenTest(Puzzle puz, Set set, int numHiddenNums) {
        List<Integer> possibleHiddenNums = new ArrayList<Integer>();
        List<List<Square>> squaresWithPossHN = new ArrayList<List<Square>>();
        for (int i = 1; i < 10; i++) {
            List<Square> sqList = set.squaresWithNumStillPos(i);
            if (sqList.size() <= numHiddenNums && sqList.size() > 0 /* maybe 1?? */) {
                possibleHiddenNums.add(i);
                squaresWithPossHN.add(sqList);
            }
        }
        // I have all the possible hidden numbers as well as the squares
        // that they belong to. I now need to see if there is overlap and
        // hence hiddenNumbers.

        // Only one possible hidden Num, does not work because looking for
        // at least two
        if (possibleHiddenNums.size() < 2) {
            return false;
        }
        else if (numHiddenNums == 2) {
            for (int i = 0; i < possibleHiddenNums.size(); i++) {
                for (int j = i+1; j < possibleHiddenNums.size(); j++) {
                    List<Square> sqList = Globals.listConcat(squaresWithPossHN.get(i), squaresWithPossHN.get(j));
                    if (sqList.size() <= numHiddenNums) {
                        int first = possibleHiddenNums.get(i);
                        int second = possibleHiddenNums.get(j);
                        List<Integer> hiddenNums = new ArrayList<Integer>(Arrays.asList(first, second));
                        boolean change = hiddenNumbersFound(hiddenNums, sqList, puz, set);
                        if (change) {
                            return true;
                        }
                    }
                }
            }
        }
        else if (numHiddenNums == 3) {
            for (int i = 0; i < possibleHiddenNums.size(); i++) {
                for (int j = i+1; j < possibleHiddenNums.size(); j++) {
                    List<Square> sqPartialList = Globals.listConcat(squaresWithPossHN.get(i), squaresWithPossHN.get(j));
                    for (int k = j+1; k < possibleHiddenNums.size(); k++) {
                        List<Square> sqList = Globals.listConcat(sqPartialList, squaresWithPossHN.get(k));
                        if (sqList.size() <= numHiddenNums) {
                            int first = possibleHiddenNums.get(i);
                            int second = possibleHiddenNums.get(j);
                            int third = possibleHiddenNums.get(k);
                            List<Integer> hiddenNums = new ArrayList<Integer>(Arrays.asList(first, second, third));
                            boolean change = hiddenNumbersFound(hiddenNums, sqList, puz, set);
                            if (change) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        else if (numHiddenNums == 4) {
            for (int i = 0; i < possibleHiddenNums.size(); i++) {
                for (int j = i+1; j < possibleHiddenNums.size(); j++) {
                    List<Square> sqPartialList = Globals.listConcat(squaresWithPossHN.get(i), squaresWithPossHN.get(j));
                    for (int k = j+1; k < possibleHiddenNums.size(); k++) {
                        List<Square> sqPartialList2 = Globals.listConcat(sqPartialList, squaresWithPossHN.get(k));
                        for (int f = k+1; f < possibleHiddenNums.size(); f++) {
                            List<Square> sqList = Globals.listConcat(sqPartialList2, squaresWithPossHN.get(f));
                            if (sqList.size() <= numHiddenNums) {
                                int first = possibleHiddenNums.get(i);
                                int second = possibleHiddenNums.get(j);
                                int third = possibleHiddenNums.get(k);
                                int fourth = possibleHiddenNums.get(f);
                                List<Integer> hiddenNums = new ArrayList<Integer>(Arrays.asList(first, second, third, fourth));
                                boolean change = hiddenNumbersFound(hiddenNums, sqList, puz, set);
                                if (change) {
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }

        return false;
    }

    public static boolean hiddenNumbersFound(List<Integer> hiddenNums, List<Square> sqList, Puzzle puz, Set set) {
        boolean change = false;
        List<Integer> oppoNums = oppoNums(hiddenNums);
        PuzzleEvent event = new PuzzleEvent(puz, sqList, false, "HiddenNums", oppoNums);
        String eventString = "Hidden Numbers " + Integer.toString(hiddenNums.size()) + ": ";
        for (Square sq : sqList) {
            eventString += sq.name() + " can't be ";
            List<Integer> removeList = sq.inversePoss(hiddenNums);
            for (int h : removeList) {
                eventString += Integer.toString(h) + " ";
                sq.removePossible(h);
                change = true;
            }
        }
        if (change) {
            eventString += "because there are the hidden numbers ";
            for (int j : hiddenNums) {
                eventString += Integer.toString(j) + " ";
            }
            eventString += " in " + set.returnName();
            event.setReason(eventString);
            puz.addEvent(event);
            return true;
        }
        return false;
    }

    public static List<Integer> oppoNums(List<Integer> nums) {
        List<Integer> result = new ArrayList<Integer>();
        for (int i = 1; i < 10; i++) {
            if (!nums.contains(i)) {
                result.add(i);
            }
        }
        return result;
    }

}
