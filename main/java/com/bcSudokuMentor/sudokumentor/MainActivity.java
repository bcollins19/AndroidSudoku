package com.bcSudokuMentor.sudokumentor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Button resumeGame = (Button) findViewById(R.id.ResumeGame);
		Button newGame = (Button) findViewById(R.id.NewGame);
		Button inputGame = (Button) findViewById(R.id.InputGame);
		Button techniques = (Button) findViewById(R.id.TechniquesDescriptions);

		//Listening to button event
		resumeGame.setOnClickListener(new View.OnClickListener() {

			public void onClick(View arg0) {
				//Starting a new Intent
				Intent nextScreen = new Intent(getApplicationContext(), PuzzleActivity.class);
				nextScreen.putExtra("New or Resume", "Resume");
				startActivity(nextScreen);

			}
		});

		newGame.setOnClickListener(new View.OnClickListener() {

			public void onClick(View arg0) {
				//Starting a new Intent
				Intent nextScreen = new Intent(getApplicationContext(), PuzzleActivity.class);
				//Puzzle puz = GenerateSudoku.NewSudoku();
				Log.e("HEY", "about to genPuzzle");
				Puzzle puz2 = GenerateSudoku2.genPuzzle(500);
				String puzzleString = puz2.puzToString();
				nextScreen.putExtra("puzzleString", puzzleString);
				nextScreen.putExtra("New or Resume", "New");
				startActivity(nextScreen);

			}
		});

		inputGame.setOnClickListener(new View.OnClickListener() {

			public void onClick(View arg0) {
				//Starting a new Intent
				Intent nextScreen = new Intent(getApplicationContext(), InputActivity.class);
				startActivity(nextScreen);

			}
		});

		techniques.setOnClickListener(new View.OnClickListener() {

			public void onClick(View arg0) {
				//Starting a new Intent
				Intent nextScreen = new Intent(getApplicationContext(), TechniqueActivity.class);
				startActivity(nextScreen);

			}
		});
	}
}