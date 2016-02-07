package com.bcSudokuMentor.sudokumentor;

/**
 * Created by Brett on 8/30/2015.
 */
public class BackTrackingNextSquare {

    Square[] squares;
    int numberSetNeeds;

    public BackTrackingNextSquare() {
        squares = null;
        numberSetNeeds = 0;
    }

    public int numSquares() {
        if (squares == null) {
            return 10;
        }
        return squares.length;
    }

    public String btnsToString() {
        String result = "Squares ";
        if (squares != null) {
            if (squares.length == 0) {
                result += "none";
            }
            else {
                for (Square sq : squares) {
                    result += sq.name() + " ";
                }
            }
        }
        result += "numberSetNeeds " + Integer.toString(numberSetNeeds);
        return result;
    }
}
