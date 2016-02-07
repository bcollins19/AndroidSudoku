package com.bcSudokuMentor.sudokumentor;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.GridView;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class PuzzleActivity extends ActionBarActivity {

    Controller cont;

    public Button getDelPosButton() {
        return (Button) findViewById(R.id.deletePos);
    }

    public Button getShowPosButton() {
        return (Button) findViewById(R.id.showPossiblesButton);
    }

    public void onBackPressed() {
        try {
            String savedPuzzle = cont.displayPuzzle.puzToString();
            savedPuzzle += cont.displayPuzzle.toPossString();
            FileOutputStream fOut = openFileOutput("savedPuzzle.txt", Context.MODE_PRIVATE);

            OutputStreamWriter osw = new OutputStreamWriter(fOut);

            osw.write(savedPuzzle);

            osw.close();
        } catch (IOException ioe) {
            {ioe.printStackTrace();}
        }
        Intent nextScreen = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(nextScreen);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.puzzle_activity);
        Intent i = getIntent();
        String puzzleString = i.getStringExtra("puzzleString");
        if (puzzleString == null) {
            puzzleString = "000000094760910050090002081070050010000709000080031067240100070010090045900000100";
            //puzzleString = "000000000904607000076804100309701080708000301051308702007502610005403208000000000";
        }

        boolean resume = false;

        String newOrResume = i.getStringExtra("New or Resume");
        if (newOrResume != null && newOrResume.equals("Resume")) {
            String totalPuzzle = "";
            try {
                InputStream inputStream = openFileInput("savedPuzzle.txt");
                if (inputStream != null) {
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    String receiveString = "";
                    StringBuilder stringBuilder = new StringBuilder();

                    while ((receiveString = bufferedReader.readLine()) != null) {
                        stringBuilder.append(receiveString);
                    }

                    inputStream.close();
                    puzzleString = stringBuilder.toString();
                    resume = true;
                }
            } catch (FileNotFoundException ioe) {
                Log.e("HEY", "cant find fiie");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        cont = new Controller(puzzleString, resume);
        cont.solvedPuzzle.solvePuzzle();

		/*
		RelativeLayout gridview = (RelativeLayout) findViewById(R.id.puzzle);
		LayoutParams params = gridview.getLayoutParams();
		params.width *= .8;
		gridview.setLayoutParams(params); */

        GridView bottomPanel = (GridView) findViewById(R.id.bottomPanel);
        BottomPanel bottom = new BottomPanel((Context) this, cont);
        bottomPanel.setAdapter(bottom);



        PuzzleButtons stuff = new PuzzleButtons(this, cont, puzzleString, bottom);

        bottom.finishInitialize(stuff);

        GridView set0 = (GridView) findViewById(R.id.Set0);
        set0.setAdapter(stuff.getSetAdapter(0));

        GridView set1 = (GridView) findViewById(R.id.Set1);
        set1.setAdapter(stuff.getSetAdapter(1));

        GridView set2 = (GridView) findViewById(R.id.Set2);
        set2.setAdapter(stuff.getSetAdapter(2));

        GridView set3 = (GridView) findViewById(R.id.Set3);
        set3.setAdapter(stuff.getSetAdapter(3));

        GridView set4 = (GridView) findViewById(R.id.Set4);
        set4.setAdapter(stuff.getSetAdapter(4));

        GridView set5 = (GridView) findViewById(R.id.Set5);
        set5.setAdapter(stuff.getSetAdapter(5));

        GridView set6 = (GridView) findViewById(R.id.Set6);
        set6.setAdapter(stuff.getSetAdapter(6));

        GridView set7 = (GridView) findViewById(R.id.Set7);
        set7.setAdapter(stuff.getSetAdapter(7));

        GridView set8 = (GridView) findViewById(R.id.Set8);
        set8.setAdapter(stuff.getSetAdapter(8));


        stuff.setBottomPanel(bottom);


        Button nextMoveButton = (Button) findViewById(R.id.nextMoveButton);
        nextMoveButton.setOnClickListener(new ButtonOnClickListener(cont, 0, (Context) this, bottom, this));

        Button hintButton = (Button) findViewById(R.id.hintButton);
        hintButton.setOnClickListener(new ButtonOnClickListener(cont, 1, (Context) this, bottom, this));

        Button possibleButton = (Button) findViewById(R.id.showPossiblesButton);
        possibleButton.setOnClickListener(new ButtonOnClickListener(cont, 2, (Context) this, bottom, this));

        Button delPos = (Button) findViewById(R.id.deletePos);
        delPos.setOnClickListener(new ButtonOnClickListener(cont, 3, (Context) this, bottom, this));
        //delPos.setBackgroundColor(Globals.SQUARE_COLOR);

        bottom.doClick(0);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

