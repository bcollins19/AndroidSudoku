package com.bcSudokuMentor.sudokumentor;

//import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;


public class BottomPanelOnClickListener implements OnClickListener{
	int index;
	Controller cont;
	Context mContext;
	BottomPanel view;
	PuzzleButtons puz;

	
	//public final static int hintColor = 0xff000;
	
	public BottomPanelOnClickListener(int index, Controller controller, Context context, BottomPanel textView, PuzzleButtons puzzle) {
		this.index = index;
		this.cont = controller;
		mContext = context;
		view = textView;
		puz = puzzle;
	}
	
	@Override
	public void onClick(View v) {
		for (int i = 0; i < 81; i++) {
			if (cont.displayPuzzle.getSquare(i/9, i%9).value() == index+1) {
				puz.setColor(i, Globals.HIGHLIGHT_COLOR);
			}
			else if (cont.displayPuzzle.getSquare(i/9, i%9).isPossible(index+1)) {
				if (cont.showPos == true) {
					if (cont.puzButtons.getColor(i) != Globals.SHOW_COLOR ) {
						puz.setColor(i, Globals.POSSIBLE_COLOR);
					}
				}
				else {
					if (cont.puzButtons.getColor(i) != Globals.SHOW_COLOR) {
						puz.setColor(i, Globals.SQUARE_COLOR);
					}
				}
			}
			else {
				if (cont.puzButtons.getColor(i) != Globals.SHOW_COLOR) {
					puz.setColor(i, Globals.SQUARE_COLOR);
				}

			}
		}
		for (int i = 0; i < view.getCount(); i++) {
			view.setColor(i, Globals.SQUARE_COLOR);
		}
		cont.numberSet(Integer.parseInt((String) this.view.getItem(this.index)));
		v.setBackgroundColor(Globals.HIGHLIGHT_COLOR);
		/*
		AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
		alertDialog.setTitle("Alert Dialog");
		alertDialog.setMessage((String) this.view.getItem(this.index));
		alertDialog.show(); 
		*/
	}
}
