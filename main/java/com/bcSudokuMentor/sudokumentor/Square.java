package com.bcSudokuMentor.sudokumentor;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;
//import com.bcSudokuMentor.sudokumentorPuzzle;
//import com.bcSudokuMentor.sudoumentor.Set;

class Square {

    // data that the squares need to know
    private Puzzle puzzle;
    private Set row;
    private Set col;
    private Set box;
    private int value;
    private boolean[] possibles;
    private int rowNum;
    private int colNum;


    // function: Square
    // input: a name and the three sets the square is in
    // creates a square with the specified information
    // and tells the sets that it owns this square
    public Square(Set r, Set c, Set b, Puzzle myPuzzle, int rowN, int colN) {
        this.puzzle = myPuzzle;
        this.row = r;
        this.col = c;
        this.box = b;
        this.value = 0;
        this.row.ownSquare(this);
        this.col.ownSquare(this);
        this.box.ownSquare(this);
        possibles = new boolean[9];
        for (int index = 0; index < 9; index++) {
            possibles[index] = true;
        }
        this.rowNum = rowN;
        this.colNum = colN;
    }


    // function: name
    // returns the name of the square
    public String name() {
        return "r" + Integer.toString(rowNum+1) + "c" + Integer.toString(colNum+1);
    }

    public int value() {
        return value;
    }

    // function: isSolved
    // returns true if a square is solved, false otherwise
    public boolean isSolved() {
        if (value == 0) {
            return false;
        }
        return true;
    }

    // function: isPossible
    // input: an integer
    // returns true if that number is a possibility for the square
    public boolean isPossible(int n) {
        if (this.isSolved()) {
            return false;
        }
        if (possibles[n - 1]) {  // has to be n-1 because array starts at 0
            return true;
        }
        return false;
    }



    // function: solvedWith
    // input: an integer and a string for the reason
    // makes it so the square is solved with that integer and tells everything
    public void solvedWith(int solvedNum) {
        if (this.isSolved()) {
            return;
        }
        this.value = solvedNum;
        this.row.squareSolved(this, solvedNum);
        this.col.squareSolved(this, solvedNum);
        this.box.squareSolved(this, solvedNum);
    }

    public void backTrackingGuess(int guess) {
        this.value = guess;
        this.row.squareSolved(this, guess);
        this.col.squareSolved(this, guess);
        this.box.squareSolved(this, guess);
    }

    public int getRowNum() {
        return rowNum;
    }

    public int getColNum() {
        return colNum;
    }

    public void setEqualTo(Square sq) {
        this.value = sq.value;
        for (int index = 0; index < 9; index++) {
            possibles[index] = sq.possibles[index];
        }
    }

    public int sqRandPoss() {
        int[] poss = new int[this.numPossible()];
        int index = 0;
        for (int i = 1; i < 10; i++) {
            if (this.isPossible(i)) {
                poss[index] = i;
                index++;
            }
        }
        Random rand = new Random();
        int randIndex = rand.nextInt(poss.length);
        return poss[randIndex];
    }


    // function: solvedWith
    // input: an integer and a string for the reason
    // makes it so the square is solved with that integer and tells everything
    public void otherSquareSolved(int notPossible) {
        if (this.isSolved()) {
            return;
        }
        this.removePossible(notPossible);
    }

    public int numPossible() {
        int result = 0;
        for (int i = 1; i < 10; i++) {
            if (this.isPossible(i)) {
                result++;
            }
        }
        return result;
    }

    public List<Integer> inversePoss(List<Integer> nums) {
        List<Integer> result = new ArrayList<Integer>();
        for (int i = 1; i < 10; i++) {
            if (!nums.contains(i) && this.isPossible(i)) {
                result.add(i);
            }
        }
        return result;
    }

    // funtion: whichSet
    // input: a square
    // output: an array of Sets so that it can tell what sets it is in
    public Set[] whichSet() {
        Set[] Sets = new Set[3];
        Sets[0] = this.row;
        Sets[1] = this.col;
        Sets[2] = this.box;
        return Sets;
    }

    public boolean[] possibles() {
        return possibles;
    }

    public Vector<Integer> intPossibles() {
        Vector<Integer> result = new Vector<Integer>(0);
        for (int i = 1; i < 10; i++) {
            if (this.isPossible(i)) {
                result.addElement(i);
            }
        }
        return result;
    }

    public int[] possArray() {
        int[] result = new int[this.numPossible()];
        int index = 0;
        for (int i = 1; i < 10; i++) {
            if (this.isPossible(i)) {
                result[index] = i;
                index++;
            }
        }
        return result;
    }

    public void removeSolve() {
        this.value = 0;
    }

    // function: removePossible
    // input: an int
    // output: it will return void but will change the square so the inputted number is
    // no longer a possibility for that square
    public void removePossible(int num) {
        if (this.value != 0) {
            return;
        }
        possibles[num - 1] = false;
    }

    public int[] getPosition() {
        int[] pos = {rowNum, colNum};
        return pos;
    }

    public Set[] getSets() {
        Set[] sets = new Set[3];
        sets[0] = row;
        sets[1] = col;
        sets[2] = box;
        return sets;
    }

    public boolean sharesSetWith(Square otherSq) {
        if (row == otherSq.row) {
            return true;
        }
        else if (col == otherSq.col) {
            return true;
        }
        else if (box == otherSq.box) {
            return true;
        }
        else {
            return false;
        }
    }

    public int possThatIsNot(int val) {
        if (this.numPossible() != 2) {
            Log.e("HEY", "Error, there should be two possibilities.");
            return 0;
        }
        else if (!this.isPossible(val)) {
            Log.e("HEY", "Error, the given value should be possible.");
            return 0;
        }
        for (int i = 1; i < 10; i++) {
            if (i != val && this.isPossible(i)) {
                return i;
            }
        }
        Log.e("HEY", "Error, there should be another possible");
        return 0;
    }

    public List<Set> sharedSets(Square otherSq) {
        List<Set> sharedSets = new ArrayList<Set>();
        if (row == otherSq.row) {
            sharedSets.add(row);
        }
        if (col == otherSq.col) {
            sharedSets.add(col);
        }
        if (box == otherSq.box) {
            sharedSets.add(box);
        }
        return sharedSets;
    }

    public boolean inRadiusOf(List<Square> list) {
        for (Square sq : list) {
            if (this.sharesSetWith(sq)) {
                return true;
            }
        }
        return false;
    }

    public List<Square> overlapRadiusWithPoss(Square sq, int poss) {
        List<Square> result = new ArrayList<Square>();
        for (Set set : this.getSets()) {
            for (Square square : set.getSquares()) {
                if (square != this && square.isPossible(poss) && square != sq && square.sharesSetWith(sq)) {
                    if (!result.contains(square)) {
                        result.add(square);
                    }
                }
            }
        }
        return result;
    }

    public String possiblesString() {
        String result = "";
        for (int i = 1; i < 10; i++) {
            if (this.isPossible(i)) {
                result += Integer.toString(i + 1) + " ";
            }
        }
        result += "\n";
        return result;
    }

    public String totalPossibleString() {
        String result = "";
        for (int i = 1; i < 10; i++) {
            if (this.isPossible(i)) {
                result += Integer.toString(i);
            }
            else {
                result += "0";
            }
        }
        return result;
    }

    public List<Integer> possiblesList() {
        List<Integer> result = new ArrayList<Integer>();
        for (int i = 1; i < 10; i++) {
            if (this.isPossible(i)) {
                result.add(i);
            }
        }
        return result;
    }

    public boolean sharesDuplicateValueWithAnySquares() {
        if (this.value == 0) {
            return false;
        }
        List<Square> rowSqs = row.getSqList();
        List<Square> colSqs = col.getSqList();
        List<Square> boxSqs = box.getSqList();
        List<Square> rowAndColSqs = Globals.listConcat(rowSqs, colSqs);
        List<Square> sqList = Globals.listConcat(rowAndColSqs, boxSqs);
        for (Square sq : sqList) {
            if (sq != this) {
                if (this.value() == sq.value()) {
                    return true;
                }
            }
        }
        return false;
    }
}
