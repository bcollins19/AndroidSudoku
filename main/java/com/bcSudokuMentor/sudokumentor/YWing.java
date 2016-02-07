package com.bcSudokuMentor.sudokumentor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Brett on 9/12/2015.
 */
public class YWing {

    public static boolean YWing(Puzzle puz) {
        for (Square sq : puz.getSquares()) {
            if (sq.numPossible() == 2) {
                int[] poss = new int[2];
                int index = 0;
                for (int i = 1; i < 10; i++) {
                    if (sq.isPossible(i)) {
                        poss[index] = i;
                        index++;
                    }
                }

                List<Square> numOnePartners = findPartners(sq, poss[0]);
                List<Square> numTwoPartners = findPartners(sq, poss[1]);
                List<Integer> numOnePartnerPoss = new ArrayList<Integer>();
                List<Integer> numTwoPartnerPoss = new ArrayList<Integer>();

                for (Square square : numOnePartners) {
                    int n = square.possThatIsNot(poss[0]);
                    if (!numOnePartnerPoss.contains(n)) {
                        numOnePartnerPoss.add(n);
                    }
                }
                for (Square square : numTwoPartners) {
                    int n = square.possThatIsNot(poss[1]);
                    if (!numTwoPartnerPoss.contains(n)) {
                        numTwoPartnerPoss.add(n);
                    }
                }
                for (int possRemoval : numOnePartnerPoss) {
                    if (numTwoPartnerPoss.contains(possRemoval)) {
                        List<Square> squaresOneWithPossRemoval = squaresContainingPoss(numOnePartners, possRemoval);
                        List<Square> squaresTwoWithPossRemoval = squaresContainingPoss(numTwoPartners, possRemoval);
                        for (Square finalOneSq : squaresOneWithPossRemoval) {
                            for (Square finalTwoSq : squaresTwoWithPossRemoval) {
                                List<Square> removingPossSquare = finalOneSq.overlapRadiusWithPoss(finalTwoSq, possRemoval);
                                if (removingPossSquare.size() != 0) {
                                    String eventString = "";
                                    for (Square s : removingPossSquare) {
                                        s.removePossible(possRemoval);
                                        eventString += s.name() + " ";
                                    }
                                    eventString += "cannot be a " + Integer.toString(possRemoval) + " becuase " + sq.name() + " is the start of" +
                                            " a YWing";
                                    PuzzleEvent event = new PuzzleEvent(puz, removingPossSquare, false, eventString, possRemoval);
                                    puz.addEvent(event);
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

    public static List<Square> squaresContainingPoss(List<Square> squareList, int poss) {
        List<Square> result = new ArrayList<Square>();
        for (Square sq : squareList) {
            if (sq.isPossible(poss)) {
                result.add(sq);
            }
        }
        return result;
    }

    public static List<Square> findPartners(Square sq, int num) {
        List<Square> result = new ArrayList<Square>();
        for (Set set : sq.getSets()) {
            for (Square square : set.getSquares()) {
                if (square != sq && square.isPossible(num) && !result.contains(square) && square.numPossible() == 2) {
                    result.add(square);
                }
            }
        }
        return result;
    }

}
