package com.bcSudokuMentor.sudokumentor;

import android.util.Log;

import java.util.Random;

/**
 * Created by Brett on 8/30/2015.
 */
public class GenerateSudoku2 {

    public static Puzzle genSolvedPuzzle() {
        Log.e("HEY", "in gen solved puzzle");
        Controller cont = new Controller();
        Set box1 = cont.displayPuzzle.getSet(18);
        int[] nums = new int[9];
        for (int i = 0; i < 9; i++) {
            nums[i] = i+1;
        }
        ShuffleArray(nums);
        for (int i = 0; i < 9; i++) {
            box1.getSquare(i).solvedWith(nums[i]);
        }
        Set box2 = cont.displayPuzzle.getSet(19);
        int[] possArray = box2.getSquare(0).possArray();
        ShuffleArray(possArray);
        for (int i = 0; i < 3; i++) {
            box2.getSquare(i).solvedWith(possArray[i]);
        }
        Log.e("HEY", "going into helper");
        genSolvedPuzzleHelper(cont.displayPuzzle, cont.solvedPuzzle);
        Log.e("HEY", "out of gen solved puz helper");
        return cont.displayPuzzle;
    }

    public static boolean genSolvedPuzzleHelper(Puzzle startingPoint, Puzzle backTrackingResult) {
        int solutionCount = BackTracking.BackTracking(startingPoint, backTrackingResult);
        if (solutionCount == 0) {
            return false;
        }
        else if (solutionCount == 1) {
            startingPoint.setEqualTo(backTrackingResult);
            return true;
        }
        boolean solved = false;
        Puzzle savePuz = new Puzzle();
        savePuz.setEqualTo(startingPoint);
        Square sq = startingPoint.getSqWithLeastPoss();
        if (sq == null) {
            return false;
        }
        int[] possArray = sq.possArray();
        ShuffleArray(possArray);
        for (int i = 0; i < possArray.length; i++) {
            sq.solvedWith(possArray[i]);
            solved = genSolvedPuzzleHelper(startingPoint, backTrackingResult);
            if (solved) {
                return true;
            }
            startingPoint.setEqualTo(savePuz);
        }
        return false;
    }


    public static void ShuffleArray(int[] nums) {
        Random rnd = new Random();
        for (int i = 0; i < nums.length; i++) {
            int index = rnd.nextInt(nums.length);
            int a = nums[index];
            nums[index] =  nums[i];
            nums[i] = a;
        }
    }

    public static Puzzle genPuzzle(int maxIterations) {
        Puzzle puz = genSolvedPuzzle();
        int startingNum = maxIterations;
        Puzzle hardestPuz = new Puzzle();
        hardestPuz.setEqualTo(puz);
        PuzzleStratsUsed hardestStrats = new PuzzleStratsUsed();
        Random randPos = new Random();
        int difficulty = 0;
        while (!hardestStrats.harderThan(Globals.harderThanThisStrat) && maxIterations > 0) {
            for (int i = 0; i < 50; i++) {
                maxIterations--;
                if (maxIterations % 1000 == 0) {
                    Log.e("HEY", Integer.toString(startingNum - maxIterations) + " iterations done");
                }
                int pos = randPos.nextInt(81);

                int nums = puz.getNumberSolved();
                if (nums <= 17) {
                    continue;
                }
                while (!puz.getSquare(pos / 9, pos % 9).isSolved()) {
                    pos = randPos.nextInt(81);
                }
                Puzzle removePuz = new Puzzle();
                removePuz.setEqualTo(puz);
                removePuz.getSquare(pos / 9, pos % 9).removeSolve();
                puz.genPuzzSetEqualTo(removePuz);
                Puzzle copyPuz = new Puzzle();
                copyPuz.setEqualTo(puz);
                Puzzle solution = new Puzzle();
                int[] backTrackArray = BackTracking.BackTrackingWithDifficulty(puz, solution);
                int numSolutions = backTrackArray[0];
                int branchDifficulty = backTrackArray[1];
                if (numSolutions == 0 || numSolutions > 1) {
                    //iterations++;
                    continue;
                }
                int currentDifficulty = branchDifficulty*100 + 81 - copyPuz.getNumberSolved();
                PuzzleStratsUsed strats = puz.solvePuzzle();
                if (strats.harderThan(hardestStrats)) {
                    hardestStrats.setEqualTo(strats);
                    hardestPuz.setEqualTo(copyPuz);
                    difficulty = branchDifficulty;

                }
                else if (strats.atLeastAsHardAs(hardestStrats) && currentDifficulty > difficulty) {
                    hardestStrats.setEqualTo(strats);
                    hardestPuz.setEqualTo(copyPuz);
                    difficulty = currentDifficulty;
                }
                puz.setEqualTo(copyPuz);
            }
            puz.setEqualTo(hardestPuz);
        }
        Log.e("HEY", hardestStrats.stratsToString());
        Log.e("HEY", "num iters: " + Integer.toString(startingNum - maxIterations));
        return hardestPuz;
    }


}
