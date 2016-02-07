package com.bcSudokuMentor.sudokumentor;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Brett on 8/22/2015.
 */
public class PuzzleEvent {
    private List<Square> affectedSquares;
    private boolean isSolveEvent;
    private String reason;
    private List<Integer> num;
    private Puzzle puz;

    public PuzzleEvent(Puzzle puzzle, List<Square> squares, boolean isSolve, String strat, int number) {
        affectedSquares = squares;
        isSolveEvent = isSolve;
        reason = strat;
        num = new ArrayList<Integer>(Arrays.asList(number));
        puz = puzzle;
    }

    public PuzzleEvent(Puzzle puzzle, List<Square> squares, boolean isSolve, String strat, List<Integer> number) {
        affectedSquares = squares;
        isSolveEvent = isSolve;
        reason = strat;
        num = number;
        puz = puzzle;
    }

    public List<Square> getAffectedSquares() {
        return affectedSquares;
    }

    public boolean isSolveEvent() {
        return isSolveEvent;
    }

    public String getReason() {
        return reason;
    }

    public List<Integer> getNumbers() {
        return num;
    }

    public void setReason(String strat) {
        reason = strat;
    }

    public static PuzzleEvent copyPuzzleEvent(PuzzleEvent event, Puzzle thisPuz) {
        List<Square> affSqList = new ArrayList<Square>();
        for (Square sq : event.getAffectedSquares()) {
            int rowNum = sq.getRowNum();
            int colNum = sq.getColNum();
            affSqList.add(thisPuz.getSquare(rowNum, colNum));
        }
        return new PuzzleEvent(thisPuz, affSqList, event.isSolveEvent, event.reason, event.num);
    }

    public void addSquare(Square sq) {
        if (affectedSquares == null) {
            affectedSquares = new ArrayList<Square>(Arrays.asList(sq));
            return;
        }
        else {
            affectedSquares.add(sq);
            return;
        }
    }

    public static void printEvents(List<PuzzleEvent> eventList) {
        for (int i = 0; i < eventList.size(); i++) {
            String logString = "";
            Log.e("HEY", eventList.get(i).getReason());
        }
    }

    public void printEvent() {
        Log.e("HEY", getReason());
    }

    public void addNumToList(int number) {
        num.add(number);
    }
}
