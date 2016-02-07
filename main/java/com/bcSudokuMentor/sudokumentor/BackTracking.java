package com.bcSudokuMentor.sudokumentor;

import java.util.List;

/**
 * Created by Brett on 8/21/2015.
 */
public class BackTracking {

    public static int BackTracking(Puzzle input, Puzzle output) {
        Puzzle initialPuz = new Puzzle();
        initialPuz.setEqualTo(input);

        int[] result = BackTrackingHelper2(input, output);
        input.setEqualTo(initialPuz);

        return result[0];
    }

    public static int[] BackTrackingWithDifficulty(Puzzle input, Puzzle output) {
        Puzzle initialPuz = new Puzzle();
        initialPuz.setEqualTo(input);
        int[] result = BackTrackingHelper2(input, output);
        input.setEqualTo(initialPuz);
        return result;
    }

    /*
    public static int[] BackTrackingHelper(Puzzle input, Puzzle output) {
        int[] result = {0, 0};
        if (input.getNumberSolved() < 17) {
            result[0] = Globals.BACKTRACKINGMAXCOUNT;
            return result;
        }
        Square sqWithLeastPoss = input.getSqWithLeastPoss();
        // Puzzle is already solved
        if (sqWithLeastPoss == null) {
            output.setEqualTo(input);
            result[0] = 1;
            return result;
        }
        int numPoss = sqWithLeastPoss.numPossible();
        if (numPoss == 0) {
            result[0] = 0;
            return result;
        }
        else {
            int branches = (numPoss - 1) * (numPoss - 1);
            int count = 0;
            Controller cont = new Controller();
            cont.solvedPuzzle.setEqualTo(input);
            List<Integer> numsToTry = sqWithLeastPoss.possiblesList();
            for (int i = 0; i < numsToTry.size(); i++) {
                sqWithLeastPoss.backTrackingGuess(numsToTry.get(i));
                if (input.validPuzzle()) {
                    int[] recurseSol = BackTrackingHelper(input, output);
                    count += recurseSol[0];
                    branches += recurseSol[1];
                    if (count >= Globals.BACKTRACKINGMAXCOUNT) {
                        recurseSol[0] = Globals.BACKTRACKINGMAXCOUNT;
                        recurseSol[1] = branches;
                        return recurseSol;
                    }
                }
                input.setEqualTo(cont.solvedPuzzle);
            }
            result[0] = count;
            result[1] = branches;
            return result;
        }
    }
*/
    public static int[] BackTrackingHelper2(Puzzle input, Puzzle output) {
        int[] result = {0, 0};
        if (input.getNumberSolved() < 17) {
            result[0] = Globals.BACKTRACKINGMAXCOUNT;
            return result;
        }
        Square sqWithLeastPoss = input.getSqWithLeastPoss();
        // Puzzle is already solved
        if (sqWithLeastPoss == null) {
            output.setEqualTo(input);
            result[0] = 1;
            return result;
        }
        int numPoss = sqWithLeastPoss.numPossible();
        if (numPoss == 0) {
            result[0] = 0;
            return result;
        }
        int branches;
        int count = 0;
        BackTrackingNextSquare setSquares  = input.backTrackingSetSquares();
        int guess = 0;
        boolean useGuess = false;
        if (setSquares.squares == null) {
            result[0] = 0;
            return result;
        }
        if (setSquares.squares.length < numPoss) {
            Puzzle copyPuz = new Puzzle();
            copyPuz.setEqualTo(input);
            branches = (setSquares.squares.length - 1) * (setSquares.squares.length - 1);
            for (Square sq : setSquares.squares) {
                sq.backTrackingGuess(setSquares.numberSetNeeds);
                if (input.validPuzzle()) {
                    int[] recurseSol = BackTrackingHelper2(input, output);
                    count += recurseSol[0];
                    branches += recurseSol[1];
                    if (count >= Globals.BACKTRACKINGMAXCOUNT) {
                        recurseSol[0] = Globals.BACKTRACKINGMAXCOUNT;
                        recurseSol[1] = branches;
                        return recurseSol;
                    }
                }
                input.setEqualTo(copyPuz);
            }
            result[0] = count;
            result[1] = branches;
            return result;
        }
        else {
            branches = (numPoss - 1) * (numPoss - 1);
            Controller cont = new Controller();
            cont.solvedPuzzle.setEqualTo(input);
            List<Integer> numsToTry = sqWithLeastPoss.possiblesList();
            for (int i = 0; i < numsToTry.size(); i++) {
                sqWithLeastPoss.backTrackingGuess(numsToTry.get(i));
                if (input.validPuzzle()) {
                    int[] recurseSol = BackTrackingHelper2(input, output);
                    count += recurseSol[0];
                    branches += recurseSol[1];
                    if (count >= Globals.BACKTRACKINGMAXCOUNT) {
                        recurseSol[0] = Globals.BACKTRACKINGMAXCOUNT;
                        recurseSol[1] = branches;
                        return recurseSol;
                    }
                }
                input.setEqualTo(cont.solvedPuzzle);
            }
            result[0] = count;
            result[1] = branches;
            return result;
        }
    }
}
