package com.bcSudokuMentor.sudokumentor;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;





public class BottomPanel extends BaseAdapter{
	private Context mContext;
	private String numbers = "";
	private Controller cont;
	private PuzzleButtons puz;
	
	private TextView[] views = new TextView[9];
	
	public BottomPanel(Context c, Controller controller) {
		mContext = c;
		numbers += "123456789";
		cont = controller;
		cont.panelButtons = this;
	}

    // Need this function because both PuzzleButtons and BottomPanel
    // need an instance of each other, so we create BottomPanel
    // then create an instance of PuzzleButtons with it and finally
    // use that created instance to finalize the BottomPanel instance.
	public void finishInitialize(PuzzleButtons puzzle) {
		for (int i = 0; i < 9; i++) {
            views[i] = new TextView(mContext);
            views[i].setLayoutParams(new android.widget.AbsListView.LayoutParams(Globals.dpToPixel(30), Globals.dpToPixel(40)));
            views[i].setText(numbers.substring(i, i + 1));
            views[i].setBackgroundColor(Globals.SQUARE_COLOR);
            views[i].setTextColor(Globals.TEXT_COLOR);
            views[i].setGravity(Gravity.CENTER);
            views[i].setOnClickListener(new BottomPanelOnClickListener(i, cont, mContext, this, puzzle));
        }
		puz = puzzle;
	}

	public Context getmContext() {
		return mContext;
	}

	public void setButtonAdapter(PuzzleButtons but) {
		puz = but;
	}
	
	public Controller getController() {
		return cont;
	}
	
	public int getCount() {
		return 9;
	}
	
	public Object getItem(int position) {
		return numbers.substring(position, position+1);
	}
	
	public long getItemId(int position) {
		return position;
	}
	
	public TextView getTextView(int position) {
		return views[position];
	}
	
	public PuzzleButtons getPuzzleButtons() {
		return puz;
	}
	
	public void setColor(int position, int color) {
		views[position].setBackgroundColor(color);
	}
	
	public TextView getView(int position, View convertView, ViewGroup parent) {
        return views[position];
	}
	
	public void doClick(int position) {
		views[position].performClick();
	}
}
