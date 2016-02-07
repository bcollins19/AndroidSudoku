package com.bcSudokuMentor.sudokumentor;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;


class Set {
	private String name; // name of the set
	private Square[] SquaresOwned;
	private Puzzle puzzle;
	private int nextSquare;

	// function name: Set
	// input: a name for a set
	// makes a set with the input name and creates an array of "squaresowned"
	public Set(String setName, Puzzle myPuzzle) {
		name = setName;
		puzzle = myPuzzle;
		SquaresOwned = new Square[9];
		nextSquare = 0;
	}

	public Puzzle getPuzzle() {
		return puzzle;
	}

	public Square getSquare(int index) {
		return this.SquaresOwned[index];
	}

	// function: ownSquare
	// input: a square
	// tells a set that it owns the inputted square
	public void ownSquare(Square sq) {
		SquaresOwned[nextSquare] = sq;
		nextSquare++;

	}

    public Square[] getSquares() {
        return SquaresOwned;
    }


	public Square getSquareAtIndex(int index) {
		return SquaresOwned[index];
	}



	public int numSquaresPos(int num) {
		int result = 0;
		for (int i = 0; i < 9; i++) {
			if (SquaresOwned[i].isPossible(num)) {
				result++;
			}
		}
		return result;
	}

	public Vector<Square> posSquares(int num) {
		Vector<Square> result = new Vector<Square>(0);
		for (int i = 0; i < 9; i++) {
			if (SquaresOwned[i].isPossible(num)) {
				result.addElement(SquaresOwned[i]);
			}
		}
		return result;
	}

	public List<Square> posExceptTheseSquares(Square sq1, Square sq2, int num) {
		List<Square> result = new ArrayList<Square>();
		for (int i = 0; i < 9; i++) {
			if (SquaresOwned[i] != sq1 && SquaresOwned[i] != sq2) {
				if (SquaresOwned[i].isPossible(num)) {
					result.add(SquaresOwned[i]);
				}
			}
		}
		return result;
	}

	public String returnName() {
		return name;
	}

	public void squareSolved(Square square, int removeNum) {
		for (int index = 0; index < 9; index++) {
			if (SquaresOwned[index] != square) {
				SquaresOwned[index].otherSquareSolved(removeNum);
			}
		}
	}

	public List<Square> squaresWithNumStillPos(int num) {
		List<Square> result = new ArrayList<Square>();
		for (Square sq : SquaresOwned) {
			if (sq.isPossible(num)) {
				result.add(sq);
			}
		}
		return result;
	}


	public String setToString() {
		String result = "";
		for (int i = 0; i < 9; i++) {
			result += Integer.toString(SquaresOwned[i].value()) + " ";
		}
		return result;
	}

	public boolean validSet() {
		List<Integer> ints = new ArrayList<Integer>();
		for (Square sq : SquaresOwned) {
			if (sq.value() != 0) {
				if (ints.contains(sq.value())) {
					return false;
				}
				ints.add(sq.value());
			}
		}
		return true;
	}

	public List<Square> getSqList() {
		List<Square> sqList = new ArrayList<Square>();
		for (Square sq : SquaresOwned) {
			sqList.add(sq);
		}
		return sqList;
	}

}