package com.bcSudokuMentor.sudokumentor;

//import java.util.Scanner;
//import java.util.Vector;


import java.util.List;

public class Controller {

	Puzzle solvedPuzzle;
	Puzzle displayPuzzle;
	PuzzleButtons puzButtons;
	BottomPanel panelButtons;
	int numberSet;
	boolean showPos;
	boolean delPos;

	public Controller(String puzNumbers, boolean fullPuzzle) {
		numberSet = 1;
		solvedPuzzle = new Puzzle(this);
		displayPuzzle = new Puzzle(this);
		showPos = false;
		delPos = false;
		this.givens(puzNumbers.substring(0, 81), solvedPuzzle);
		this.givens(puzNumbers.substring(0,81), displayPuzzle);
		if (fullPuzzle) {
			displayPuzzle.reducePossibles(puzNumbers.substring(81));
			solvedPuzzle.reducePossibles(puzNumbers.substring(81));
		}
		//solvedPuzzle.solvePuzzle();
	}

	public Controller() {
		numberSet = 1;
		solvedPuzzle = new Puzzle(this);
		displayPuzzle = new Puzzle(this);
		showPos = false;
		delPos = false;

	}

	public void SetPuzzle(String puzNumbers) {
		solvedPuzzle = new Puzzle(this);
		displayPuzzle = new Puzzle(this);
		this.givens(puzNumbers, solvedPuzzle);
		this.givens(puzNumbers, displayPuzzle);
	}

	public void numberSet(int number) {
		this.numberSet = number;
	}


	public static void program(Controller cont) {
		Puzzle puzzle = new Puzzle(cont);
		Puzzle displayPuzzle = new Puzzle(cont);

		//Scanner scanner = new Scanner (System.in);
		//System.out.println("Enter the numbers in a long string with 0's as blanks: ");
		//String numbers = scanner.next();
		String puzNumbers = "000907000900000008030405020307040206000509000809020103070604030200000009000102000";
		int puzImportanceToShow = 1;
		cont.givens(puzNumbers, puzzle);
		cont.givens(puzNumbers, displayPuzzle);
		//Scanner importanceToShow = new Scanner (System.in);
		//System.out.println("What level of technique difficulty should be shown? 0 being everything 3 being only the toughest technique. ");
		//puzzle.setImportance(Integer.parseInt(importanceToShow.next()));
		//puzzle.getController().printPuzzle(puzzle);
		puzzle.solvePuzzle();
		//puzzle.getController().printPuzzle(puzzle);
		//System.out.println("");
		//System.out.println("");
		//Puzzle userPuzzle = new Puzzle(cont);
	    
	    /*
	     * This is just old code used before I had the Sudoku app built
	    while (!userPuzzle.puzzleSolved(puzzle)) {
	      //Scanner userInput = new Scanner (System.in);
	      //System.out.println("What do you want to do next?");
	      //String nextJob = userInput.next();
	      //if (nextJob.equals("hint")) {
	        //System.out.println(puzzle.nextHint());
	      //}
	      //else if (nextJob.equals("finish")) {
	        //puzzle.getController().printPuzzle(puzzle);
	        break;
	      }
	      else if (nextJob.length() == 81) {
	        cont.givens(nextJob, userPuzzle);
	        Vector<Square> wrongSquares = userPuzzle.puzzleOnTrack(puzzle);
	        if (wrongSquares != null) {
	          for (int i = 0; i < wrongSquares.size(); i++) {
	            System.out.println(wrongSquares.elementAt(i).name() + " is not matching our solution");
	          }
	        }
	      }
	      else {  
	       System.out.println("We did not recognize your command.");
	      }
	    }
	    */
	}


	public Puzzle givens(String numbers, Puzzle puzzle) {
		/*
	    if (numbers.length() != 81) {
	      Scanner scanner = new Scanner (System.in);
	      System.out.println("You must enter a string of length 81");
	      String newNumbers = scanner.next();
	      return givens(newNumbers, puzzle);
	    } 
	    else {
	      for (int i = 0; i < 81; i++) {
	    	  
	    	  if (numbers.charAt(i) < 48 || numbers.charAt(i) >= 58) {
	    		  Scanner scanner = new Scanner (System.in);
	    	      System.out.println("Sorry that is not only numbers");
	    	      String newNumbers = scanner.next();
	    	      return givens(newNumbers, puzzle);
	          }
	      } */
		int row = 0;
		int col = 0;
		for (int index = 0; index < 81; index++) {
			row = index / 9;
			col = index % 9;

			// 48 in ascii is 0
			if (numbers.charAt(index) > 48 && numbers.charAt(index) < 58) {
				// I need to offset by 48 because there is where 0 starts
				puzzle.getSquare(row, col).solvedWith(numbers.charAt(index) - 48);
			}
		}
	      /*
	      if (!puzzle.validPuzzle()) {
	        System.out.println("You did not give me a valid Sudoku");
	        puzzle.getController().printPuzzle(puzzle);
	        Scanner scannerNew = new Scanner (System.in);
	        System.out.println("Please give me a valid Sudoku");
	        String newNumbers = scannerNew.next();
	        puzzle = givens(newNumbers, puzzle);
	      } */
		return puzzle;
		//}
	}

	public Square getNextSolvedSq() {
		List<PuzzleEvent> solvedEvents = this.solvedPuzzle.getPuzzleEvents();
		for (int i = 0; i < solvedEvents.size(); i++) {
			if (solvedEvents.get(i).isSolveEvent()) {
				Square solvedSq = solvedEvents.get(i).getAffectedSquares().get(0);
				if (!displayPuzzle.getSquare(solvedSq.getRowNum(), solvedSq.getColNum()).isSolved()) {
					return solvedSq;//displayPuzzle.getSquare(solvedSq.getRowNum(), solvedSq.getColNum());
				}
			}
		}
		return null;
	}

	public boolean isUserPuzzleSqSolved(int rowNum, int colNum) {
		Square sq = this.displayPuzzle.getSquare(rowNum, colNum);
		return sq.isSolved();
	}

	public PuzzleEvent getNextHint() {
		for (int i = 0; i < solvedPuzzle.getPuzzleEvents().size(); i++) {
			PuzzleEvent event = solvedPuzzle.getPuzzleEvents().get(i);
			List<Square> sqList = event.getAffectedSquares();
			List<Integer> nums = event.getNumbers();
			if (event.isSolveEvent()) {
				int row = sqList.get(0).getRowNum();
				int col = sqList.get(0).getColNum();
				if (!displayPuzzle.getSquare(row, col).isSolved()) {
					event.printEvent();
					return event;
				}
			} else {
				for (Square sq : sqList) {
					int row = sq.getRowNum();
					int col = sq.getColNum();
					for (int removePos : nums) {
						if (displayPuzzle.getSquare(row, col).isPossible(removePos)) {
							event.printEvent();
							return event;
						}
					}
				}
			}
		}
		return null;
	}

	public PuzzleButtons getPuzzleButtons() {
		return puzButtons;
	}
}