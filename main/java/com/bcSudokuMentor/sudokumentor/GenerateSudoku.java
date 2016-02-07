package com.bcSudokuMentor.sudokumentor;

import android.util.Log;

import java.util.Random;

/**
 * Created by Brett on 8/29/2015.
 */
public class GenerateSudoku {

    public static Puzzle NewSudoku() {
        Controller cont = new Controller();
        PuzzleStratsUsed strats = new PuzzleStratsUsed();
        boolean bool = NewSudokuHelper(cont.displayPuzzle, cont.solvedPuzzle, 0, strats);
        if (!bool) {
            Log.e("HEY", "Can't create a new puzzle");
            return null;
        }
        int round = 1;/*
        while (!strats.usedSetClaim) {
            Log.e("HEY", Integer.toString(round));
            round++;
            PuzzleStratsUsed newStrats = new PuzzleStratsUsed();
            Controller newCont = new Controller();
            bool = NewSudokuHelper(newCont.displayPuzzle, newCont.solvedPuzzle, 0, newStrats);
            strats.setEqualTo(newStrats);
        } */
        Log.e("HEY", cont.displayPuzzle.puzToString());
        PuzzleStratsUsed as = cont.solvedPuzzle.solvePuzzle();
        Log.e("HEY", as.stratsToString());
        return cont.displayPuzzle;
    }

    public static boolean NewSudokuHelper(Puzzle puz, Puzzle helpPuz, int numHints, PuzzleStratsUsed strats) {
        int numSolutions = BackTracking.BackTracking(puz, helpPuz);
        helpPuz.setEqualTo(puz);
        if (numSolutions == 0) {
            return false;
        }
        if (numSolutions == 1) {
            /*
            Globals.useBackTracking = false;
            strats = helpPuz.solvePuzzle();
            Globals.useBackTracking = true;
            if (helpPuz.fullySolved()) {
                return true;
            }
            helpPuz.setEqualTo(puz); */
            Log.e("HEY","found one");
            return true;
        }
        Random posRand = new Random();
        boolean puzzleFound = false;
        while (!puzzleFound) {
            int nextPos = posRand.nextInt(80);
            if (!puz.getSquare(nextPos/9, nextPos % 9).isSolved()) {
                int nextNum = puz.getSquare(nextPos / 9, nextPos % 9).sqRandPoss();
                if (puz.getSquare(nextPos / 9, nextPos % 9).isPossible(nextNum)) {
                    Controller cont = new Controller();
                    cont.displayPuzzle.setEqualTo(puz);
                    cont.solvedPuzzle.setEqualTo(helpPuz);
                    puz.getSquare(nextPos / 9, nextPos % 9).solvedWith(nextNum);
                    helpPuz.getSquare(nextPos / 9, nextPos % 9).solvedWith(nextNum);
                    Log.e("HEY", "put a " + Integer.toString(nextNum) + " in " + puz.getSquare(nextPos / 9, nextPos % 9).name());
                    PuzzleStratsUsed newStrats = new PuzzleStratsUsed();
                    strats.setEqualTo(newStrats);
                    puzzleFound = NewSudokuHelper(puz, helpPuz, numHints + 1, strats);
                    if (puzzleFound) {
                        return true;
                    }
                    puz.setEqualTo(cont.displayPuzzle);
                    helpPuz.setEqualTo(cont.solvedPuzzle);
                }
            }
        }
        return false;
    }

    public static void ShuffleArray(int[] nums) {
        Random rnd = new Random();
        for (int i = nums.length -1; i > 0; i--) {
            int index = rnd.nextInt(i+1);
            int a = nums[index];
            nums[index] =  nums[i];
            nums[i] = a;
        }
    }
}
