package com.bcSudokuMentor.sudokumentor;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class InputActivity extends Activity {

    EditText puzzleText;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_layout);

        puzzleText = (EditText) findViewById(R.id.inputText);

        final Button doneButton = (Button) findViewById(R.id.doneWithInput);

        doneButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                //Starting a new Intent
                String puzzleString = puzzleText.getText().toString();
                if (puzzleString.length() != 81) {
                    AlertDialog alertDialog = new AlertDialog.Builder(InputActivity.this).create();
                    alertDialog.setTitle("Sorry!");
                    alertDialog.setMessage("There must be 81 characters, it is now " + Integer.toString(puzzleString.length()) + " and is " + puzzleString);
                    alertDialog.show();
                } else {
                    boolean goodPuzzle = true;
                    int countGivens = 0;
                    for (int i = 0; i < 81; i++) {
                        if (puzzleString.charAt(i) < 48 || puzzleString.charAt(i) >= 58) {
                            goodPuzzle = false;
                            AlertDialog alertDialog = new AlertDialog.Builder(InputActivity.this).create();
                            alertDialog.setTitle("Sorry!");
                            alertDialog.setMessage("At least one of the characters is not numeric");
                            alertDialog.show();
                            return;
                        }
                        else if (puzzleString.charAt(i) > 48 && puzzleString.charAt(i) < 59) {
                            countGivens++;
                        }
                    }
                    if (countGivens < 17) {
                        goodPuzzle = false;
                        AlertDialog alertDialog = new AlertDialog.Builder(InputActivity.this).create();
                        alertDialog.setTitle("Sorry!");
                        alertDialog.setMessage("Sudokus have at least 17 hints given to begin with");
                        alertDialog.show();
                        return;
                    }
                    Controller cont = new Controller(puzzleString, false);
                    if (!cont.solvedPuzzle.validGivens()) {
                        AlertDialog alertDialog = new AlertDialog.Builder(InputActivity.this).create();
                        alertDialog.setTitle("Sorry!");
                        alertDialog.setMessage("The input Sudoku puzzle conflicts itself (the same number is duplicated in a set)");
                        alertDialog.show();
                        return;
                    }

                    int numberOfSolutions = BackTracking.BackTracking(cont.solvedPuzzle, cont.displayPuzzle);
                    if (numberOfSolutions == 0) {
                        AlertDialog alertDialog = new AlertDialog.Builder(InputActivity.this).create();
                        alertDialog.setTitle("Sorry!");
                        alertDialog.setMessage("This is not a valid Sudoku, there are 0 solutions.");
                        alertDialog.show();
                        goodPuzzle = false;
                        return;
                    }
                    else if(numberOfSolutions > 1) {
                        AlertDialog alertDialog = new AlertDialog.Builder(InputActivity.this).create();
                        alertDialog.setTitle("Sorry!");
                        alertDialog.setMessage("This is not a valid Sudoku, there are many solutions.");
                        alertDialog.show();
                        goodPuzzle = false;
                        return;
                    }
                    if (goodPuzzle) {
                        Intent nextScreen = new Intent(InputActivity.this, PuzzleActivity.class);
                        nextScreen.putExtra("puzzleString", puzzleString);
                        startActivity(nextScreen);
                    } else {
                        AlertDialog alertDialog = new AlertDialog.Builder(InputActivity.this).create();
                        alertDialog.setTitle("Sorry!");
                        alertDialog.setMessage("At least one of the characters is not numeric");
                        alertDialog.show();
                    }

                }


            }
        });
    }



}