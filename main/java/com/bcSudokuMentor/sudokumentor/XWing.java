package com.bcSudokuMentor.sudokumentor;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
/**
 * Created by Brett on 1/9/2015.
 */
public class XWing {

    public static boolean XWing(Puzzle puz, int[] numOrder) {
        for (int i = 0; i < numOrder.length; i++) {
            boolean result = CheckNumXWing(puz, numOrder[i]);
            if (result == true) {
                return true;
            }
        }
        return false;
    }

    public static boolean CheckNumXWing(Puzzle puz, int num) {
        for (int i = 0; i < 3; i++) {
            int offset = 9 * i;
            for (int firstSet = offset; firstSet < 8 + offset; firstSet++) {
                int numPosSet1 = puz.getSet(firstSet).numSquaresPos(num);
                // Found a set with the num and only two possible squares
                if (numPosSet1 == 2) {
                    for (int secondSet = firstSet + 1; secondSet < 9 + offset; secondSet++) {
                        int numPosSet2 = puz.getSet(secondSet).numSquaresPos(num);
                        // Found another set of same "type" row v col v box with 2 possible squares
                        if (numPosSet2 == 2) {
                            boolean change = false;
                            String eventString = "XWing: ";
                            PuzzleEvent event = new PuzzleEvent(puz, new ArrayList<Square>(), false, "", new ArrayList<Integer>());
                            Vector<Square> locationsSet1 = puz.getSet(firstSet).posSquares(num);
                            Vector<Square> locationsSet2 = puz.getSet(secondSet).posSquares(num);
                            Square set1sq1 = locationsSet1.elementAt(0);
                            Square set1sq2 = locationsSet1.elementAt(1);
                            Square set2sq1 = locationsSet2.elementAt(0);
                            Square set2sq2 = locationsSet2.elementAt(1);
                            // Have to see if we remove any other possibles!
                            Set intersectSet1 = puz.shareSet(set1sq1, set2sq1);
                            Set intersectSet2 = puz.shareSet(set1sq2, set2sq2);
                            if (intersectSet1 != null && intersectSet2 != null) {
                                List<Square> anyExtras1 = intersectSet1.posExceptTheseSquares(set1sq1, set2sq1, num);
                                List<Square> anyExtras2 = intersectSet2.posExceptTheseSquares(set1sq2, set2sq2, num);
                                for (Square sq : anyExtras1) {
                                    eventString += sq.name() + " ";
                                    event.addSquare(sq);
                                    sq.removePossible(num);
                                    change = true;
                                }
                                for (Square sq : anyExtras2) {
                                    eventString += sq.name() + " ";
                                    event.addSquare(sq);
                                    sq.removePossible(num);
                                    change = true;
                                }
                            }
                            if (change) {
                                eventString += "can't be a " + Integer.toString(num) + " because of an XWing in " +
                                        set1sq1.name() + " " + set1sq2.name() + " " + set2sq1.name() + " " + set2sq2.name() + " ";
                                event.setReason(eventString);
                                event.addNumToList(num);
                                puz.addEvent(event);
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

}
