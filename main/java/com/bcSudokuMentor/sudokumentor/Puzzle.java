package com.bcSudokuMentor.sudokumentor;


import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
class Puzzle {

    // data that puzzle has
    private Set[] sets;
    private Square[][] square;
    private List<PuzzleEvent> puzzleEvents;
    private int atHintNumber;
    private int[] numOrder;
    private Controller controller;
    private PuzzleEvent currentEvent;
    private boolean usesBackingTrackingToSolve = false;

    // function createPuzzle
    // creates 27 sets and 81 squares in a 9 by 9 array
    // first 9 sets are "rows" second 9 sets are "cols" and
    // third are "boxes"

    public Puzzle() {
        sets = new Set[27];
        square = new Square[9][9];
        puzzleEvents = new ArrayList<PuzzleEvent>();
        atHintNumber = 0;
        numOrder = new int[9];
        for (int i = 0; i < 9; i++) {
            numOrder[i] = 9 - i;
        }
        for (int i = 0; i < 27; i++) {
            if (i < 9) {
                sets[i] = new Set("row" + Integer.toString(i + 1), this);
            } else if (i > 8 && i < 18) {
                sets[i] = new Set("col" + Integer.toString(i % 9 + 1), this);
            } else {
                sets[i] = new Set("box" + Integer.toString(i % 9 + 1), this);
            }
        }
        for (int row = 0; row <= 8; row++) {
            for (int col = 0; col <= 8; col++) {
                int boxnum = ((row) / 3) * 3 + ((col) / 3);
                square[row][col] = new Square(sets[row], sets[col + 9], sets[boxnum + 18], this, row, col);
            }
        }
    }

    public Puzzle(Controller cont) {
        sets = new Set[27];
        square = new Square[9][9];
        puzzleEvents = new ArrayList<PuzzleEvent>();
        atHintNumber = 0;
        numOrder = new int[9];
        for (int i = 0; i < 9; i++) {
            numOrder[i] = 9 - i;
        }
        controller = cont;
        for (int i = 0; i < 27; i++) {
            if (i < 9) {
                sets[i] = new Set("row" + Integer.toString(i + 1), this);
            } else if (i > 8 && i < 18) {
                sets[i] = new Set("col" + Integer.toString(i % 9 + 1), this);
            } else {
                sets[i] = new Set("box" + Integer.toString(i % 9 + 1), this);
            }
        }
        for (int row = 0; row <= 8; row++) {
            for (int col = 0; col <= 8; col++) {
                int boxnum = ((row) / 3) * 3 + ((col) / 3);
                square[row][col] = new Square(sets[row], sets[col + 9], sets[boxnum + 18], this, row, col);
            }
        }
    }

    public boolean getUsesBackTrackingToSolve() {
        return usesBackingTrackingToSolve;
    }

    public int getNumberSolved() {
        int count = 0;
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                Square sq = getSquare(r, c);
                if (sq.isSolved()) {
                    count++;
                }
            }
        }
        return count;
    }

    public BackTrackingNextSquare backTrackingSetSquares() {
        BackTrackingNextSquare result = new BackTrackingNextSquare();
        for (Set set : sets) {
            for (int num = 1; num < 10; num++) {
                int count = 0;
                for (Square sq : set.getSquares()) {
                    if (sq.isPossible(num)) {
                        count++;
                    }
                }
                if (count < result.numSquares() && count > 0) {
                    result.squares = new Square[count];
                    int index = 0;
                    for (Square sq : set.getSquares()) {
                        if (sq.isPossible(num)) {
                            result.squares[index] = sq;
                            index++;
                        }
                    }
                    result.numberSetNeeds = num;
                }
            }
        }
        return result;
    }

    public Set getSet(int setNumber) {
        return this.sets[setNumber];
    }

    public Square getSquare(int row, int col) {
        return this.square[row][col];
    }

    public int getHintNum() {
        return atHintNumber;
    }

    public void incrementHintNum() {
        atHintNumber++;
    }

    public int getReasonSize() {
        return puzzleEvents.size();
    }

    public void setCurrentEvent(PuzzleEvent event) {
        currentEvent = event;
    }

    public PuzzleEvent getCurrentEvent() {
        return currentEvent;
    }

    public void reducePossibles(String possibles) {
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                for (int i = 0; i < 9; i++) {
                    int start = r*81 + c*9 + i;
                    if (Integer.parseInt(possibles.substring(start, start+1)) == 0) {
                        getSquare(r, c).removePossible(i+1);
                    }
                }
            }
        }
    }

    public Set shareSet(Square sq1, Square sq2) {
        Set[] sets1 = sq1.whichSet();
        Set[] sets2 = sq2.whichSet();
        if (sets1[0] == sets2[0]) {
            return sets1[0];
        } else if (sets1[1] == sets2[1]) {
            return sets1[1];
        } else if (sets1[2] == sets2[2]) {
            return sets1[2];
        } else {
            return null;
        }
    }

    public PuzzleStratsUsed solvePuzzle() {
        PuzzleStratsUsed strats = new PuzzleStratsUsed();
        if (this.isSolved()) {
            return strats;
        }
        boolean restart = false;
        if (Globals.useNeedNumber) {
            for (int index = 0; index < 9; index++) {
                for (int set = 0; set < 27; set++) {
                    restart = NeedNumber.needNumberInSet(this, sets[set], numOrder[index]);
                    if (restart == true) {
                        strats.usedNeedNumber = true;
                        strats.merge(solvePuzzle());
                        return strats;
                    }
                }
            }
        }
        if (Globals.useSetClaim) {
            for (int index = 0; index < 9; index++) {
                for (int set = 0; set < 27; set++) {
                    restart = SetClaim.setClaim(this, sets[set], numOrder[index]);
                    if (restart == true) {
                        strats.usedSetClaim = true;
                        strats.merge(solvePuzzle());
                        return strats;
                    }
                }
            }
        }
        if (Globals.useOnlyNum) {
            for (int set = 0; set < 27; set++) {
                restart = OnlyNum.onlyNumInSquare(this, sets[set]);
                if (restart == true) {
                    strats.usedOnlyNum = true;
                    strats.merge(solvePuzzle());
                    return strats;
                }
            }
        }
        if (Globals.useNakedNumbers) {
            for (int number = 2; number < 5; number++) {
                for (int set = 0; set < 27; set++) {
                    restart = NakedNumbers.nakedNumbers(this, sets[set], number);
                    if (restart == true) {
                        strats.usedNakedNumbers = true;
                        strats.merge(solvePuzzle());
                        return strats;
                    }
                }
            }
        }
        if (Globals.useHiddenNumbers) {
            for (int set = 0; set < 27; set++) {
                restart = HiddenNumbers.hiddenNumbers(this);
                if (restart) {
                    strats.usedHiddenNumbers = true;
                    strats.merge(solvePuzzle());
                    return strats;
                }
            }
        }
        if (Globals.useXWing) {
            restart = XWing.XWing(this, numOrder);
            if (restart == true) {
                strats.usedXWing = true;
                strats.merge(solvePuzzle());
                return strats;
            }
        }
        if (Globals.useColorChains) {
            for (int i : numOrder) {
                restart = ColorChains.ColorChains(this, i);
                if (restart) {
                    strats.usedColorChains = true;
                    strats.merge(solvePuzzle());
                    return strats;
                }
            }
        }
        if (Globals.useYWing) {
            restart = YWing.YWing(this);
            if (restart) {
                strats.usedYWing = true;
                strats.merge(solvePuzzle());
                return strats;
            }
        }
        if (Globals.useXCycle) {
            for (int i = 1; i < 10; i++) {
                restart = XCycle.XCycle(this, i);
                if (restart) {
                    strats.usedXCycle = true;
                    strats.merge(solvePuzzle());
                    return strats;
                }
            }
        }
        if (Globals.useBackTracking) {
            Controller c = new Controller();
            int numSolutions = BackTracking.BackTracking(this, c.solvedPuzzle);
            if (numSolutions == 1) {
                this.setEqualTo(c.solvedPuzzle);
                Log.e("HEY", "numsolutions is 1");
                usesBackingTrackingToSolve = true;
                strats.usedBackTracking = true;
                return strats;
            } else {
                Log.e("HEY", "more than one solution");
                return strats;
            }
        }
        return strats;
    }

    public Controller getController() {
        return controller;
    }


    public boolean validPuzzle() {
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if (!square[r][c].isSolved() && square[r][c].numPossible() == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean validGivens() {
        for (Set set : sets) {
            if (!set.validSet()) {
                return false;
            }
        }
        return true;
    }

    public Vector<Square> puzzleOnTrack(Puzzle solution) {
        Vector<Square> result = new Vector<Square>(0);
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (this.square[row][col].value() != solution.square[row][col].value() && this.square[row][col].value() != 0) {
                    result.addElement(this.square[row][col]);
                }
            }
        }
        return result;
    }

    public boolean puzzleSolved(Puzzle solution) {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (this.square[row][col].value() != solution.square[row][col].value()) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean fullySolved() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (!this.square[row][col].isSolved()) {
                    return false;
                }
            }
        }
        return true;
    }

    public String puzToString() {
        String result = "";
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                result += Integer.toString(this.getSquare(r, c).value());
            }
        }
        return result;
    }

    public String toPossString() {
        String result = "";
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                result += this.getSquare(r, c).totalPossibleString();
            }
        }
        return result;
    }

    public boolean isSolved() {
        for (int i = 0; i < 9; ++i) {
            for (int j = 0; j < 9; ++j) {
                if (!square[i][j].isSolved()) {
                    return false;
                }
            }
        }
        return true;
    }

    public void backTrackingSetEqual(Puzzle puz) {
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                square[r][c].setEqualTo(puz.square[r][c]);
            }
        }
    }

    public Square[] getSquares() {
        Square[] squares = new Square[81];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                squares[i*9+j] = square[i][j];
            }
        }
        return squares;
    }

    public void genPuzzSetEqualTo(Puzzle puz) {
        Controller cont = new Controller();
        String puzzleString = puz.puzToString();
        cont.givens(puzzleString, cont.displayPuzzle);
        this.backTrackingSetEqual(cont.displayPuzzle);
    }

    public void setEqualTo(Puzzle puz) {
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                square[r][c].setEqualTo(puz.square[r][c]);
            }
        }
        this.puzzleEvents = new ArrayList<PuzzleEvent>();
        for (int i = 0; i < puz.puzzleEvents.size(); i++) {
            this.puzzleEvents.add(PuzzleEvent.copyPuzzleEvent(puz.puzzleEvents.get(i), this));
        }
    }

    public List<PuzzleEvent> getPuzzleEvents() {
        return puzzleEvents;
    }

    public void addEvent(PuzzleEvent event) {
        this.puzzleEvents.add(event);
    }

    public Square getSqWithLeastPoss() {
        int count = 10;
        Square sq = null;
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if (!square[r][c].isSolved()) {
                    int numPoss = square[r][c].numPossible();
                    if (numPoss < count) {
                        count = numPoss;
                        sq = square[r][c];
                    }
                }
            }
        }
        return sq;
    }


}