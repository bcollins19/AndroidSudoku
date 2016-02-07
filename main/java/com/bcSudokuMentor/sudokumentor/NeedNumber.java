package com.bcSudokuMentor.sudokumentor;


import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;

class NeedNumber {

	// function: strategy1
	// input: a set and a number
	// if it finds a square that can be solved then it
	// will tell the square to be solved with that number
	public static boolean needNumberInSet(Puzzle puz, Set set, int checkNum) {
		if (numSquaresPoss(set, checkNum) != 1) {
			return false;
		} else {
			for (int index = 0; index < 9; index++) {
				if (set.getSquare(index).isPossible(checkNum)) {
					set.getSquare(index).solvedWith(checkNum);
					String eventString = "Need Number: " + set.returnName() + " needs a " + Integer.toString(checkNum) + " and it can only go in " +
							set.getSquare(index).name();
					PuzzleEvent event = new PuzzleEvent(puz, new ArrayList<Square>(Arrays.asList(set.getSquare(index))), true, eventString
							, checkNum);
					puz.addEvent(event);
					return true;
				}
			}
			return false;
		}
	}


	private static int numSquaresPoss(Set set, int checkNum) {
		int numPossibles = 0;
		for (int index = 0; index < 9; index++) {
			if (set.getSquare(index).isPossible(checkNum)) {
				numPossibles++;
			}
		}
		return numPossibles;
	}

}
