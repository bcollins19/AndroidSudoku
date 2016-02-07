package com.bcSudokuMentor.sudokumentor;

import java.util.ArrayList;
import java.util.Vector;
/**
 * Created by Brett on 7/7/2015.
 */
public class NakedNumbers {

    /* sizeNakedNums should be 2, 3, or 4
     * BUG: This does not handle the case where all the squares have less
     * than the sizeNakedNums for possibilities. IE when sizeNakedNums
     * is 3 then square possibilities of (1,2), (2, 3), (1,3) is a naked
     * triple but will not be caught by this current implementation. Same
     * for when sizeNakedNums is 4. However, it will work when at least one
     * of the squares has the same number of possibilities as sizeNakedNums.
     */
    public static boolean nakedNumbers(Puzzle puz, Set set, int sizeNakedNums) {
        Vector<Square> possibleSquares = squaresWithNumPossOrLess(set, sizeNakedNums);
        // Checks for nakednumbers when one square has max sizeNakedNums possibilities
        for (int i = 0; i < possibleSquares.size(); i++) {
            if (possibleSquares.elementAt(i).numPossible() == sizeNakedNums) {
                Vector<Square> nakedSet = new Vector<Square>(0);
                nakedSet.addElement(possibleSquares.elementAt(i));
                for (int j = 0; j < possibleSquares.size(); j++) {
                    // Make sure it is not the same square
                    if (j != i) {
                        if (subsetPossibilities(possibleSquares.elementAt(i), possibleSquares.elementAt(j))) {
                            nakedSet.addElement(possibleSquares.elementAt(j));
                        }
                    }
                }
                if (nakedSet.size() == sizeNakedNums) {
                    Vector<Integer> nakedNums = possibleSquares.elementAt(i).intPossibles();
                    PuzzleEvent event = new PuzzleEvent(puz, new ArrayList<Square>(), false, "NakedNumBum", new ArrayList<Integer>());
                    String eventString = "Naked Pair Sq: Square(s) ";
                    boolean change = false;
                    for (int k = 0; k < nakedNums.size(); k++) {
                        event.addNumToList(nakedNums.elementAt(k));
                        for (int square = 0; square < 9; square++) {
                            if (!nakedSet.contains(set.getSquare(square))) {
                                if (set.getSquare(square).isPossible(nakedNums.elementAt(k))) {
                                    change = true;
                                    if (!event.getAffectedSquares().contains(set.getSquare(square))) {
                                        event.addSquare(set.getSquare(square));
                                        eventString += set.getSquare(square).name() + " ";
                                    }
                                    set.getSquare(square).removePossible(nakedNums.elementAt(k));
                                    //Log.e("HEY", "Naked Pair Sq: " + set.getSquare(square).name() + " cannot be " + Integer.toString(nakedNums.elementAt(k)) + " because it is already claimed in " + squareNames(nakedSet));
                                }
                            }
                        }
                    }
                    if (change) {
                        eventString += "can't be a ";
                        String numsString = "";
                        for (int nums : nakedNums) {
                            numsString += Integer.toString(nums) + " ";
                        }
                        eventString += numsString;
                        eventString += " because ";
                        for (int nakedSetIndex = 0; nakedSetIndex < nakedSet.size(); nakedSetIndex++) {
                            eventString += nakedSet.elementAt(nakedSetIndex).name() + " ";
                        }
                        eventString += " are a naked set and need " + numsString;
                        event.setReason(eventString);
                        puz.addEvent(event);
                        return change;
                    }
                }
            }
        }
        // Gotten here so nothing so far so need to check the case for when
        // there are nakednumbers but the square doesn't have sizeNakedNum
        // possibilities. ADD THIS! (Figure it out first)
        return false;
    }

    public static String squareNames(Vector<Square> nakedSet) {
        String result = "";
        for (int i = 0; i < nakedSet.size(); i++) {
            result += nakedSet.elementAt(i).name();
            result += " ";
        }
        return result;
    }

    public static boolean subsetPossibilities(Square parent, Square possibleChild) {
        for (int i = 1; i < 10; i++) {
            if (possibleChild.isPossible(i) && !parent.isPossible(i)) {
                return false;
            }
        }
        return true;
    }

    /* Gets all the squares that have less than or equal to num possibilities
     * left.
     */
    public static Vector<Square> squaresWithNumPossOrLess(Set set, int num) {
        Vector<Square> result = new Vector<Square>(0);
        for (int square = 0; square < 9; square++) {
            int numPosForSquare = 0;
            for (int val = 1; val < 10; val++) {
                if (set.getSquare(square).isPossible(val)) {
                    numPosForSquare++;
                }
            }
            if (numPosForSquare <= num && numPosForSquare != 0) {
                result.add(set.getSquare(square));
            }
        }
        return result;
    }
}
