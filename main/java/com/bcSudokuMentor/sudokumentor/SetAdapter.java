package com.bcSudokuMentor.sudokumentor;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SetAdapter extends BaseAdapter{
	private Context mContext;
	private Controller cont;
	private TextView[] setViews = new TextView[9];
	private String numbers = "";
	private PuzzleButtons parent;
	private int setNum;
	private BottomPanel bottom;
	
	
	public SetAdapter(int setN, Context m, Controller controller, String nums, PuzzleButtons par, BottomPanel bot) {
		setNum = setN;
		mContext = m;
		cont = controller;
		numbers += nums;
		parent = par;
		bottom = bot;
		for (int i = 0; i < 9; i++) {
			setViews[i] = new TextView(mContext);
			// Was just 120, 160 without the function
			android.widget.AbsListView.LayoutParams param = new android.widget.AbsListView.LayoutParams(Globals.dpToPixel(30), Globals.dpToPixel(40));
			setViews[i].setLayoutParams(param);
			if (!numbers.substring(i, i+1).equals("0")) {
				setViews[i].setText(numbers.substring(i, i+1));
			}
			setViews[i].setPadding(2,  2,  2, 2);
			setViews[i].setBackgroundColor(Globals.SQUARE_COLOR);
			setViews[i].setTextColor(Globals.TEXT_COLOR);
	        setViews[i].setGravity(Gravity.CENTER);
	        setViews[i].setOnClickListener(new PuzzleOnClickListener(convertSetAndNumToPos(setNum, i), cont, mContext, parent, bot));
		}
	}
	
	private int convertSetAndNumToPos(int set, int num) {
		int offset = (set%3)*3;
		int offset2 = (set/3)*27;
		int offset3 = (num/3)*9;
		int value = offset+offset2+offset3+num%3;
		/*
		AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
		alertDialog.setTitle("Sorry!");
		alertDialog.setMessage("position: " + Integer.toString(value) + " set: " + Integer.toString(set) + " pos: " + Integer.toString(num))8;
		alertDialog.show();
		*/
		return value;
	}
	
	public Controller getController() {
		return cont;
	}
	
	public int getCount() {
		return 9;
	}
	
	public Object getItem(int position) {
		return cont.displayPuzzle.getSquare(position/9, position%9).value();
		//return numbers.substring(position, position+1);
	}
	
	public long getItemId(int position) {
		return position;
	}
	
	public int getColor(int position) {
		ColorDrawable i = (ColorDrawable) setViews[position].getBackground();
		return i.getColor();
	}
	
	public TextView getView(int position, View convertView, ViewGroup parent) {
		//setViews[position].setOnClickListener(new PuzzleOnClickListener(convertSetAndNumToPos(setNum, position), cont, mContext, this.parent));
        return setViews[position];
	}
	
	public void setText(int position, int number) {
		setViews[position].setText(Integer.toString(number));
	}
	
	public void setColor(int position, int color) {
		setViews[position].setBackgroundColor(color);
	}
}
