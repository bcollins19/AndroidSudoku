package com.bcSudokuMentor.sudokumentor;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class ButtonOnClickListener implements OnClickListener{
	Controller cont;
	Context mContext;
	int buttonType;
	BottomPanel bottom;
    PuzzleActivity activity;
	Drawable c;
	
	public ButtonOnClickListener(Controller controller, int whichButton, Context m, BottomPanel bot, PuzzleActivity act) {
		cont = controller;
		buttonType = whichButton;
		mContext = m;
		bottom = bot;
        activity = act;
	}
	
	@Override
	public void onClick(View v) {
		Puzzle solution = cont.solvedPuzzle;
		// Next Move Button
		if (buttonType == 0) {
			PuzzleEvent event = cont.getNextHint();
			cont.solvedPuzzle.setCurrentEvent(event);
			if (event == null) {
				AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
				alertDialog.setTitle("Sorry");
				alertDialog.setMessage("There is no more help I can give, but if there is a unique soltuion I do know it (from" +
						" a quick back tracking search algorithm)");
				alertDialog.show();
				return;
			}
			if (event.isSolveEvent()) {
				if (cont.showPos) {
					Button showPos = activity.getShowPosButton();
					showPos.performClick();
				}
				for (int i = 0; i < 81; i++) {
					if (Globals.SHOW_COLOR == cont.puzButtons.getColor(i)) {
						cont.puzButtons.setColor(i, Globals.SQUARE_COLOR);
					}
				}
				Square solSq = event.getAffectedSquares().get(0);
				int[] posArr = solSq.getPosition();
				int pos = 9 * posArr[0] + posArr[1];
				cont.puzButtons.setColor(pos, Globals.SHOW_COLOR);
				TextView panelBut = cont.panelButtons.getTextView(solSq.value() - 1);
				panelBut.performClick();
				return;
			} else {
				for (int i = 0; i < 81; i++) {
					if (Globals.SHOW_COLOR == cont.puzButtons.getColor(i)) {
						if (cont.showPos && cont.displayPuzzle.getSquare(i / 9, i % 9).isPossible(cont.numberSet)) {
							cont.puzButtons.setColor(i, Globals.POSSIBLE_COLOR);
						} else {
							cont.puzButtons.setColor(i, Globals.SQUARE_COLOR);
						}
					}
				}
				if (!cont.delPos) {
					Button delPos = activity.getDelPosButton();
					delPos.performClick();
				}
				List<Square> sqList = event.getAffectedSquares();
				List<Integer> nums = event.getNumbers();
				for (Square sq : sqList) {
					int row = sq.getRowNum();
					int col = sq.getColNum();
					for (int removePos : nums) {
						if (cont.displayPuzzle.getSquare(row, col).isPossible(removePos)) {
							cont.puzButtons.setColor((row * 9) + col, Globals.SHOW_COLOR);
						}
					}
				}
				for (int i : nums) {
					for (Square solveSq : sqList) {
						int row = solveSq.getRowNum();
						int col = solveSq.getColNum();
						Square sq = cont.displayPuzzle.getSquare(row, col);
						if (sq.isPossible(i)) {
							TextView panelBut = cont.panelButtons.getTextView(i - 1);
							panelBut.performClick();
							return;
						}
					}
				}
				return;
			}
		}
		
		// This means it is the hint button
		else if (buttonType == 1) {
			PuzzleEvent event = cont.getNextHint();
			cont.solvedPuzzle.setCurrentEvent(event);
			if (event == null) {
				AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
				alertDialog.setTitle("Sorry");
				alertDialog.setMessage("There is no more help I can give, but if there is a unique soltuion I do know it (from" +
						" a quick back tracking search algorithm)");
				alertDialog.show();
				return;
			}
			if (event.isSolveEvent()) {
				for (int i = 0; i < 81; i++) {
					if (Globals.SHOW_COLOR == cont.puzButtons.getColor(i)) {
						if (cont.displayPuzzle.getSquare(i/9, i%9).isPossible(cont.numberSet) && cont.showPos) {
							cont.puzButtons.setColor(i, Globals.POSSIBLE_COLOR);
						}
						else {
							cont.puzButtons.setColor(i, Globals.SQUARE_COLOR);
						}
					}
				}
				Square solSq = event.getAffectedSquares().get(0);
				int[] posArr = solSq.getPosition();
				int pos = 9 * posArr[0] + posArr[1];
				cont.puzButtons.setColor(pos, Globals.SHOW_COLOR);
				return;
			}
			else {
				for (int i = 0; i < 81; i++) {
					if (Globals.SHOW_COLOR == cont.puzButtons.getColor(i)) {
						if (cont.showPos && cont.displayPuzzle.getSquare(i/9, i%9).isPossible(cont.numberSet)) {
							cont.puzButtons.setColor(i, Globals.POSSIBLE_COLOR);
						}
						else {
							cont.puzButtons.setColor(i, Globals.SQUARE_COLOR);
						}
					}
				}
				List<Square> sqList = event.getAffectedSquares();
				List<Integer> nums = event.getNumbers();
				for (Square sq : sqList) {
					int row = sq.getRowNum();
					int col = sq.getColNum();
					for (int removePos : nums) {
						if (cont.displayPuzzle.getSquare(row, col).isPossible(removePos)) {
							cont.puzButtons.setColor((row * 9) + col, Globals.SHOW_COLOR);
						}
					}
				}
				return;
			}
		}
		// possibles button
		else if (buttonType == 2) {
			if (cont.showPos == false) {
				cont.showPos = true;
			}
			else {
				cont.showPos = false;
                if (cont.delPos == true) {
                    Button delPos = activity.getDelPosButton();
                    delPos.performClick();
                }
			}
			int buttonPush = cont.numberSet;
			bottom.doClick(buttonPush-1);
		}
		// delete possible button
		else if (buttonType == 3) {
			if (cont.delPos == false) {
				cont.delPos = true;
				cont.showPos = true;
				c = v.getBackground();
				//savedDeleteView = v;
                v.setBackgroundColor(Globals.DELETE_COLOR);
				bottom.doClick(cont.numberSet-1);
				AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
			}
			else {
				cont.delPos = false;
				//v = savedDeleteView;
				v.setBackground(c);
                //v.setBackgroundColor(Globals.SQUARE_COLOR);
				AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
			}
			
		}
		
	}
}
