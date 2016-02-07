package com.bcSudokuMentor.sudokumentor;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by Brett on 8/23/2015.
 */
public class TechniqueActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.technique_layout);


        final Button needNumButton = (Button) findViewById(R.id.SetNeedsNumber);
        if (Globals.useNeedNumber) {
            needNumButton.setText("Set Needs Number technique is on");
        } else {
            needNumButton.setText("Set Needs Number technique is off");
        }
        needNumButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                if (Globals.useNeedNumber) {
                    Globals.useNeedNumber = false;
                    needNumButton.setText("Set Needs Number technique is off");
                } else {
                    Globals.useNeedNumber = true;
                    needNumButton.setText("Set Needs Number technique is on");
                }
            }
        });

        final Button setClaimButton = (Button) findViewById(R.id.SetClaim);
        if (Globals.useSetClaim) {
            setClaimButton.setText("Set Claim technique is on");
        }
        else {
            setClaimButton.setText("Set Claim technique is off");
        }
        setClaimButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                if (Globals.useSetClaim) {
                    setClaimButton.setText("Set Claim technique is off");
                    Globals.useSetClaim = false;
                }
                else {
                    setClaimButton.setText("Set Claim technique is on");
                    Globals.useSetClaim = true;
                }
            }
        });

        final Button onlyNumButton = (Button) findViewById(R.id.OnlyNum);
        if (Globals.useOnlyNum) {
            onlyNumButton.setText("Only Number in Square technique is on");
        }
        else {
            onlyNumButton.setText("Only Number in Square technique is off");
        }
        onlyNumButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                if (Globals.useOnlyNum) {
                    onlyNumButton.setText("Only Number in Square technique is off");
                    Globals.useOnlyNum = false;
                }
                else {
                    onlyNumButton.setText("Only Number in Square technique is on");
                    Globals.useOnlyNum = true;
                }
            }
        });

        final Button nakedNumsButton = (Button) findViewById(R.id.NakedNums);
        if (Globals.useNakedNumbers) {
            nakedNumsButton.setText("Naked Numbers technique is on");
        }
        else {
            nakedNumsButton.setText("Naked Numbers technique is off");
        }
        nakedNumsButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                if (Globals.useNakedNumbers) {
                    nakedNumsButton.setText("Naked Numbers technique is off");
                    Globals.useNakedNumbers = false;
                }
                else {
                    nakedNumsButton.setText("Naked Numbers technique is on");
                    Globals.useNakedNumbers = true;
                }
            }
        });

        final Button hiddenNumsButton = (Button) findViewById(R.id.HiddenNums);
        if (Globals.useHiddenNumbers) {
            hiddenNumsButton.setText("Hidden Numbers technique is on");
        }
        else {
            hiddenNumsButton.setText("Hidden Numbers technique is off");
        }
        hiddenNumsButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                if (Globals.useHiddenNumbers) {
                    hiddenNumsButton.setText("Hidden Numbers technique is off");
                    Globals.useHiddenNumbers = false;
                }
                else {
                    hiddenNumsButton.setText("Hidden Numbers technique is on");
                    Globals.useHiddenNumbers = true;
                }
            }
        });

        final Button xwingButton = (Button) findViewById(R.id.XWing);
        if (Globals.useXWing) {
            xwingButton.setText("XWing technique is on");
        } else {
            xwingButton.setText("XWing technique is off");
        }
        xwingButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                if (Globals.useXWing) {
                    xwingButton.setText("XWing technique is off");
                    Globals.useXWing = false;
                } else {
                    xwingButton.setText("XWing technique is on");
                    Globals.useXWing = true;
                }
            }
        });

        final Button colorChainingButton = (Button) findViewById(R.id.ColorChaining);
        if (Globals.useColorChains) {
            colorChainingButton.setText("Color Chaining technique is on");
        } else {
            colorChainingButton.setText("Color Chaining technique is off");
        }
        colorChainingButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                if (Globals.useColorChains) {
                    colorChainingButton.setText("Color Chaining technique is off");
                    Globals.useColorChains = false;
                } else {
                    colorChainingButton.setText("Color Chaining technique is on");
                    Globals.useColorChains = true;
                }
            }
        });

        final Button yWingButton = (Button) findViewById(R.id.YWing);
        if (Globals.useYWing) {
            yWingButton.setText("YWing technique is on");
        } else {
            yWingButton.setText("YWing technique is off");
        }
        yWingButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                if (Globals.useYWing) {
                    yWingButton.setText("YWing technique is off");
                    Globals.useYWing = false;
                } else {
                    yWingButton.setText("YWing technique is on");
                    Globals.useYWing = true;
                }
            }
        });

        final Button xCycleButton = (Button) findViewById(R.id.XCycle);
        if (Globals.useXCycle) {
            xCycleButton.setText("XCycle technique is on");
        } else {
            xCycleButton.setText("XCycle technique is off");
        }
        xCycleButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                if (Globals.useXCycle) {
                    xCycleButton.setText("XCycle technique is off");
                    Globals.useXCycle = false;
                } else {
                    xCycleButton.setText("XCycle technique is on");
                    Globals.useXCycle = true;
                }
            }
        });
    }
}
