package com.bcSudokuMentor.sudokumentor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Brett on 9/12/2015.
 */
public class ColorChains {

    public static boolean ColorChains(Puzzle puz, int num) {
        Map<String, Square> visitedSqs = new HashMap<String, Square>();
        for (Square sq : puz.getSquares()) {
            if (sq.isPossible(num)) {
                if (!visitedSqs.containsKey(sq.name())) {
                    List<Square> redList = new ArrayList<Square>();
                    List<Square> blueList = new ArrayList<Square>();

                    redList.add(sq);
                    while (!everySquareVisitedInLists(redList, blueList, visitedSqs)) {
                        for (Square redSq : redList) {
                            if (!visitedSqs.containsKey(redSq.name())) {
                                for (Set set : redSq.getSets()) {
                                    Square sqAddToBlueList = getChain(set, redSq, num);
                                    if (sqAddToBlueList != null) {
                                        if (redList.contains(sqAddToBlueList)) {
                                            sqAddToBlueList.removePossible(num);
                                            String eventString = sqAddToBlueList.name() + " cannot be a " + Integer.toString(num) +
                                                    " because chaining says it must be both on and off";
                                            PuzzleEvent event = new PuzzleEvent(puz, new ArrayList<Square>(Arrays.asList(sqAddToBlueList)),
                                                    false, eventString, num);
                                            puz.addEvent(event);
                                            return true;
                                        }
                                        if (!blueList.contains(sqAddToBlueList)) {
                                            blueList.add(sqAddToBlueList);
                                        }
                                    }
                                }
                                visitedSqs.put(redSq.name(), redSq);
                            }
                        }
                        for (Square blueSq : blueList) {
                            if (!visitedSqs.containsKey(blueSq.name())) {
                                for (Set set : blueSq.getSets()) {
                                    Square sqAddToRedList = getChain(set, blueSq, num);
                                    if (sqAddToRedList != null) {
                                        if (blueList.contains(sqAddToRedList)) {
                                            sqAddToRedList.removePossible(num);
                                            String eventString = sqAddToRedList.name() + " cannot be a " + Integer.toString(num) +
                                                    " because chaining says it must be both on and off";
                                            PuzzleEvent event = new PuzzleEvent(puz, new ArrayList<Square>(Arrays.asList(sqAddToRedList)),
                                                    false, eventString, num);
                                            puz.addEvent(event);
                                            return true;
                                        }
                                        if (!redList.contains(sqAddToRedList)) {
                                            redList.add(sqAddToRedList);
                                        }
                                    }
                                }
                                visitedSqs.put(blueSq.name(), blueSq);
                            }
                        }
                    }
                    boolean change = listSqsShareSet(redList, num, puz);
                    if (change) {
                        return change;
                    }
                    change = listSqsShareSet(blueList, num, puz);
                    if (change) {
                        return change;
                    }
                    for (Square blueSq : blueList) {
                        for (Square redSq : redList) {
                            List<Square> blueRadius = new ArrayList<Square>();
                            for (Set set : blueSq.getSets()) {
                                for (Square touchedSquare : set.getSquares()) {
                                    if (touchedSquare != blueSq && touchedSquare.isPossible(num)) {
                                        blueRadius.add(touchedSquare);
                                    }
                                }
                            }
                            List<Square> crossSquares = new ArrayList<Square>();
                            for (Set set : redSq.getSets()) {
                                for (Square touchedSquare : set.getSquares()) {
                                    if (touchedSquare != redSq && touchedSquare != blueSq && blueRadius.contains(touchedSquare) &&
                                            touchedSquare.isPossible(num) && !crossSquares.contains(touchedSquare)) {
                                        crossSquares.add(touchedSquare);
                                    }
                                }
                            }
                            String eventString = "";
                            for (Square affectedSq : crossSquares) {
                                affectedSq.removePossible(num);
                                eventString += affectedSq.name() + " ";
                            }
                            if (!crossSquares.isEmpty()) {
                                eventString += "cannot be a " + Integer.toString(num) + " because " + redSq.name() +
                                        " or " + blueSq.name() + " will be a " + Integer.toString(num);
                                PuzzleEvent event = new PuzzleEvent(puz, crossSquares, false, eventString, num);
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

    public static boolean listSqsShareSet(List<Square> list, int removeNum, Puzzle puz) {
        for (Square sq1 : list) {
            for (Square sq2 : list) {
                if (sq1 != sq2 && sq1.sharesSetWith(sq2)) {
                    sq1.removePossible(removeNum);
                    sq2.removePossible(removeNum);
                    String eventString = sq1.name() + " cannot be a " + Integer.toString(removeNum) +
                            " because " + sq2.name() + " would also need to be a " + Integer.toString(removeNum);
                    PuzzleEvent event = new PuzzleEvent(puz, new ArrayList<Square>(Arrays.asList(sq1, sq2)), false, eventString, removeNum);
                    puz.addEvent(event);
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean everySquareVisitedInLists(List<Square> redList, List<Square> blueList, Map<String, Square> visitedSqs) {
        for (Square sq : redList) {
            if (!visitedSqs.containsKey(sq.name())) {
                return false;
            }
        }
        for (Square sq : blueList) {
            if (!visitedSqs.containsKey(sq.name())) {
                return false;
            }
        }
        return true;
    }

    public static Square getChain(Set set, Square square, int num) {
        int count = 0;
        Square result = null;
        for (Square sq : set.getSquares()) {
            if (sq != square && sq.isPossible(num)) {
                count++;
                result = sq;
            }
        }
        if (count == 1) {
            return result;
        }
        return null;
    }

}
