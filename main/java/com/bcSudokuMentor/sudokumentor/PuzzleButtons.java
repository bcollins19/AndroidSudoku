package com.bcSudokuMentor.sudokumentor;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class PuzzleButtons extends BaseAdapter{
	private Context mContext;
	private Controller cont;
	private SetAdapter[] setAdapters = new SetAdapter[9];
	private BottomPanel bottom;
	
	public PuzzleButtons(Context c, Controller controller, String numbers, BottomPanel bot) {
		mContext = c;
		cont = controller;
		cont.puzButtons = this;
		bottom = bot;
		for (int i = 0; i < 9; i++) {
			String nums = "";
			for (int j = 0; j < 3; j++) {
				for (int k = 0; k < 3; k++) {
					int index = (i/3)*27 + (i%3)*3 + 9*j + k; 
					nums += numbers.substring(index, index+1);
				}
			}
			setAdapters[i] = new SetAdapter(i, c, cont, nums, this, bot);
		}
		
	}
	
	public void setBottomPanel(BottomPanel bot) {
		bottom = bot;
	}
	
	public SetAdapter getSetAdapter(int pos) {
		return setAdapters[pos];
	}
	
	public Controller getController() {
		return cont;
	}
	
	private int[] convertPosToSetAndNum(int pos) {
		int[] value = new int[2];
		if ((pos >= 0 && pos <= 2) || (pos >= 9 && pos <= 11) || (pos >= 18 && pos <= 20)) {
			value[0] = 0;
			value[1] = (pos/9)*3 + pos%9;
		}
		else if ((pos >= 3 && pos <= 5) || (pos >= 12 && pos <= 14) || (pos >= 21 && pos <= 23)) {
			value[0] = 1;
			value[1] = (pos/9)*3 + (pos-3)%9;
		}
		else if ((pos >= 6 && pos <= 8) || (pos >= 15 && pos <= 17) || (pos >= 24 && pos <= 26)) {
			value[0] = 2;
			value[1] = (pos/9)*3 + (pos-6)%9;
		}
		else if ((pos >= 27 && pos <= 29) || (pos >= 36 && pos <= 38) || (pos >= 45 && pos <= 47)) {
			value[0] = 3;
			value[1] = ((pos-27)/9)*3 + pos%9;
		}
		else if ((pos >= 30 && pos <= 32) || (pos >= 39 && pos <= 41) || (pos >= 48 && pos <= 50)) {
			value[0] = 4;
			value[1] = ((pos-27)/9)*3 + (pos-3)%9;
		}
		else if ((pos >= 33 && pos <= 35) || (pos >= 42 && pos <= 44) || (pos >= 51 && pos <= 53)) {
			value[0] = 5;
			value[1] = ((pos-27)/9)*3 + (pos-6)%9;
		}
		else if ((pos >= 54 && pos <= 56) || (pos >= 63 && pos <= 65) || (pos >= 72 && pos <= 74)) {
			value[0] = 6;
			value[1] = ((pos-54)/9)*3 + pos%9;
		}
		else if ((pos >= 57 && pos <= 59) || (pos >= 66 && pos <= 68) || (pos >= 75 && pos <= 77)) {
			value[0] = 7;
			value[1] = ((pos-54)/9)*3 + (pos-3)%9;
		}
		else if ((pos >= 60 && pos <= 62) || (pos >= 69 && pos <= 71) || (pos >= 78 && pos <= 80)) {
			value[0] = 8;
			value[1] = ((pos-54)/9)*3 + (pos-6)%9;
		}
		return value;
	}
	
	public int getColor(int pos) {
		int[] position = convertPosToSetAndNum(pos);
		SetAdapter set = setAdapters[position[0]];
		int color = set.getColor(position[1]);
		return color;
	}
	
	public int getCount() {
		return 81;
	}
	
	public Object getItem(int position) {
		return cont.displayPuzzle.getSquare(position/9, position%9).value();
	}
	
	public long getItemId(int position) {
		return position;
	}
	
	public TextView getView(int position, View convertView, ViewGroup parent) {
		int[] pos = convertPosToSetAndNum(position);
		SetAdapter set = setAdapters[pos[0]];
		TextView value = set.getView(pos[1], convertView, parent);
        return value;
		
	}
	
	public void setText(int position, int number) {
		int[] pos = convertPosToSetAndNum(position);
		SetAdapter set = setAdapters[pos[0]];
		set.setText(pos[1], number);
	}
	
	public void setColor(int position, int color) {
        if (position < 81) {
            int[] pos = convertPosToSetAndNum(position);
            SetAdapter set = setAdapters[pos[0]];
            set.setColor(pos[1], color);
        }
	}

}
