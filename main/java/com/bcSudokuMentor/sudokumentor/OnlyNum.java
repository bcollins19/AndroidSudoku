package com.bcSudokuMentor.sudokumentor;


import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;

class OnlyNum {

	// funciton: onlyNum
	// takes in a set and will check every square in the set
	// and determine if there is only one number that can go
	// into the square, and if it is it will call other functions
	// such as Square's solvedWith().
	public static boolean onlyNumInSquare(Puzzle puz, Set set) {
		int count = 0;
		int possibleNum = 0;
		for (int square = 0; square < 9; square++) {
			for (int number = 1; number < 10; number++) {
				if (set.getSquare(square).isPossible(number) == true) {
					count++;
					possibleNum = number;
				}
			}
			if (count == 1) {
				set.getSquare(square).solvedWith(possibleNum);
				String eventString = "The only number that can go in " + set.getSquare(square).name() + " is a " + Integer.toString(possibleNum);
				puz.addEvent(new PuzzleEvent(puz, new ArrayList<Square>(Arrays.asList(set.getSquare(square))), true,
						eventString, possibleNum));
				return true;
			}
			count = 0;
		}
		return false;
	}

}
