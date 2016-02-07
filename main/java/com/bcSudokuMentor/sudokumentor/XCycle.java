package com.bcSudokuMentor.sudokumentor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by Brett on 9/12/2015.
 */
public class XCycle {

    public static boolean XCycle(Puzzle puz, int num) {
        List<Square[]> strongPairs = findStrongPairs(puz, num);
        for (Square[] strongPair : strongPairs) {
            for (int i = 0; i < 2; i++) {
                Square startingPoint = strongPair[i];
                Square endPoint = strongPair[1-i];
                HashSet<Square[]> visitedPairs = new HashSet<Square[]>();
                visitedPairs.add(strongPair);
                Square[] reversePair = new Square[] {strongPair[1], strongPair[0]};
                visitedPairs.add(reversePair);
                List<Square[]> XCycle = findCycle(startingPoint, startingPoint, endPoint, strongPairs, visitedPairs, num);
                if (XCycle != null) {

                    XCycle.add(strongPair);

                    List<Square> flattenedXCyle = flattenCycle(XCycle);
                    List<Square> affectedSquares = new ArrayList<Square>();
                    List<Square> onList = new ArrayList<Square>();
                    List<Square> offList = new ArrayList<Square>();
                    List<Square> visited = new ArrayList<Square>();
                    onList.add(flattenedXCyle.get(0));
                    while (visited.size() != flattenedXCyle.size()) {
                        for (Square onSq : onList) {
                            if (!visited.contains(onSq)) {
                                for (Square cycleSq : flattenedXCyle) {
                                    if (cycleSq != onSq && cycleSq.sharesSetWith(onSq) && !offList.contains(cycleSq)) {
                                        offList.add(cycleSq);
                                    }
                                }
                                visited.add(onSq);
                            }
                        }
                        for (Square offSq : offList) {
                            if (!visited.contains(offSq)) {
                                for (Square cycleSq : flattenedXCyle) {
                                    if (cycleSq != offSq && cycleSq.sharesSetWith(offSq) && !onList.contains(cycleSq)) {
                                        onList.add(cycleSq);
                                    }
                                }
                                visited.add(offSq);
                            }
                        }
                    }
                    for (Square puzSq : puz.getSquares()) {
                        if (!flattenedXCyle.contains(puzSq) && puzSq.inRadiusOf(offList) && puzSq.inRadiusOf(onList) && puzSq.isPossible(num)) {
                            affectedSquares.add(puzSq);
                        }
                    }
                    if (affectedSquares.size() != 0) {
                        String puzzleString = "";
                        boolean change = false;
                        for (Square affected : affectedSquares) {
                            if (!flattenedXCyle.contains(affected)) {
                                affected.removePossible(num);
                                puzzleString += affected.name() + " ";
                                change = true;
                            }
                        }
                        if (change) {
                            puzzleString += "cannot be a " + Integer.toString(num) + " because there is an XCycle from ";
                            for (Square cycle : flattenedXCyle) {
                                puzzleString += cycle.name() + " ";
                            }
                            PuzzleEvent event = new PuzzleEvent(puz, affectedSquares, false, puzzleString, num);
                            puz.addEvent(event);
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public static List<Square> flattenCycle(List<Square[]> cycle) {
        List<Square> result = new ArrayList<Square>();
        for (Square[] sqArray : cycle) {
            for (Square sq : sqArray) {
                if (!result.contains(sq)) {
                    result.add(sq);
                }
            }
        }
        return result;
    }

    public static List<Square[]> findCycle(Square startingPoint, Square currentPoint, Square endPoint, List<Square[]> strongPairs, HashSet<Square[]> visitedPairs, int num) {
        List<Square[]> result = new ArrayList<Square[]>();
        if (currentPoint != startingPoint && currentPoint.sharesSetWith(endPoint)) {
            return result;
        }
        for (Set set : currentPoint.getSets()) {
            for (Square sq : set.getSquares()) {
                Square[] sqPair = new Square[] {currentPoint, sq};
                if (sq != currentPoint && sq != endPoint && sq.isPossible(num) && !Contains(visitedPairs, sqPair)) {
                    for (Square[] pair : strongPairs) {
                        if ((sq == pair[0] || sq == pair[1]) && !Contains(visitedPairs, pair) &&
                                currentPoint != pair[0] && currentPoint != pair[1]) {
                            if (sq == pair[0]) {
                                Square[] reversePair = new Square[] {pair[1], pair[0]};
                                visitedPairs.add(pair);
                                visitedPairs.add(reversePair);
                                List<Square[]> recurseResult = findCycle(startingPoint, pair[1], endPoint, strongPairs, visitedPairs, num);
                                if (recurseResult != null) {
                                    recurseResult.add(pair);
                                    return recurseResult;
                                }
                            }
                            else if (sq == pair[1]) {
                                Square[] reversePair = new Square[] {pair[1], pair[0]};
                                visitedPairs.add(pair);
                                visitedPairs.add(reversePair);
                                List<Square[]> recurseResult = findCycle(startingPoint, pair[0], endPoint, strongPairs, visitedPairs, num);
                                if (recurseResult != null) {
                                    recurseResult.add(pair);
                                    return recurseResult;
                                }
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    public static boolean Contains(HashSet<Square[]> list, Square[] pair) {
        for (Square[] array : list) {
            if ((pair[0] == array[0] && pair[1] == array[1]) || (pair[0] == array[1] && pair[1] == array[0])) {
                return true;
            }
        }
        return false;
    }

    public static List<Square[]> findStrongPairs(Puzzle puz, int num) {
        List<Square[]> result = new ArrayList<Square[]>();
        for (Square sq : puz.getSquares()) {
            if (sq.isPossible(num)) {
                for (Set set : sq.getSets()) {
                    int count = 0;
                    Square pair = null;
                    for (Square possiblePair : set.getSquares()) {
                        if (possiblePair != sq && possiblePair.isPossible(num)) {
                            count++;
                            pair = possiblePair;
                        }
                    }
                    if (count == 1) {
                        Square[] strongPair = {sq, pair};
                        boolean contains = false;
                        for (Square[] pairAlready : result) {
                            if ((strongPair[0] == pairAlready[0] && strongPair[1] == pairAlready[1]) ||
                                    strongPair[0] == pairAlready[1] && strongPair[1] == pairAlready[0]) {
                                contains = true;
                            }
                        }
                        if (!contains) {
                            result.add(strongPair);
                        }
                    }
                }
            }
        }
        return result;
    }
}
