package com.bcSudokuMentor.sudokumentor;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;

public class PuzzleOnClickListener implements OnClickListener {
	Context mContext;
	Controller cont;
	int index;
	PuzzleButtons view;
	BottomPanel bottom;

	public PuzzleOnClickListener(int position, Controller controller, Context context, PuzzleButtons puzzleButtons, BottomPanel bot) {
		cont = controller;
		mContext = context;
		index = position;
		view = puzzleButtons;
		bottom = bot;
	}

	@Override
	public void onClick(View v) {
		if (cont.delPos == false) {
			if (cont.solvedPuzzle.getSquare(index / 9, index % 9).value() == cont.numberSet) {
				cont.displayPuzzle.getSquare(index / 9, index % 9).solvedWith(cont.numberSet);
				view.setText(index, cont.numberSet);
				view.setColor(index, Globals.HIGHLIGHT_COLOR);
				bottom.doClick(cont.numberSet - 1);
				if (cont.displayPuzzle.puzzleSolved(cont.solvedPuzzle) == true) {
					if (cont.displayPuzzle.fullySolved()) {
						AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
						alertDialog.setTitle("Congrats!");
						alertDialog.setMessage("You solved the Sudoku! Good work!");
						alertDialog.show();
					}
					// This is the case where the solution can not solve the puzzle and
					// the display puzzle already has everything the solution has
					else {
						AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
						alertDialog.setTitle("Sorry!");
						alertDialog.setMessage(mContext.getString(R.string.CannotSolveSudoku));
						alertDialog.show();
					}
				}
			} else if (cont.solvedPuzzle.fullySolved()) {
				AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
				alertDialog.setTitle("Sorry!");
				alertDialog.setMessage("That is not a legal move!");
				alertDialog.show();
			}
			// This is when the solved puzzle can no longer help and is unsure about itself.
			// It may be a very bad idea to do this I have to test it.
			// This now lets the user do moves that are clearly not allowed
			else {
				if (cont.displayPuzzle.getSquare(index / 9, index % 9).isPossible(cont.numberSet)) {
					cont.displayPuzzle.getSquare(index / 9, index % 9).solvedWith(cont.numberSet);
					view.setText(index, cont.numberSet);
					view.setColor(index, Globals.HIGHLIGHT_COLOR);
					bottom.doClick(cont.numberSet - 1);
				} else {
					AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
					alertDialog.setTitle("Sorry!");
					alertDialog.setMessage("That is not a legal move!");
					alertDialog.show();
				}
			}
		} else if (cont.delPos == true) {
			if (cont.solvedPuzzle.getSquare(index / 9, index % 9).value() != cont.numberSet) {
				cont.displayPuzzle.getSquare(index / 9, index % 9).removePossible(cont.numberSet);
				if (Globals.SHOW_COLOR == cont.puzButtons.getColor(index)) {
					PuzzleEvent event = cont.solvedPuzzle.getCurrentEvent();
					if (event == null) {
						cont.puzButtons.setColor(index, Globals.SQUARE_COLOR);
					}
					else {
						boolean changeColor = true;
						for (int i : event.getNumbers()) {
							if (cont.displayPuzzle.getSquare(index / 9, index % 9).isPossible(i)) {
								changeColor = false;
							}
						}
						if (changeColor) {
							cont.puzButtons.setColor(index, Globals.SQUARE_COLOR);
						}
					}
				}
				bottom.doClick(cont.numberSet - 1);
			} else {
				AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
				alertDialog.setTitle("Sorry!");
				alertDialog.setMessage("That is a possibility!");
				alertDialog.show();
			}

		}
	}
}
