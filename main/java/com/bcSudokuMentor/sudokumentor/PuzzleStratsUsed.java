package com.bcSudokuMentor.sudokumentor;

import android.util.Log;

/**
 * Created by Brett on 8/30/2015.
 */
public class PuzzleStratsUsed {
    public boolean usedNeedNumber;
    public boolean usedSetClaim;
    public boolean usedOnlyNum;
    public boolean usedNakedNumbers;
    public boolean usedHiddenNumbers;
    public boolean usedXWing;
    public boolean usedColorChains;
    public boolean usedYWing;
    public boolean usedXCycle;
    public boolean usedBackTracking;

    public PuzzleStratsUsed() {
        usedNeedNumber = false;
        usedSetClaim = false;
        usedOnlyNum = false;
        usedNakedNumbers = false;
        usedHiddenNumbers = false;
        usedXWing = false;
        usedColorChains = false;
        usedYWing = false;
        usedXCycle = false;
        usedBackTracking = false;
    }

    public void merge(PuzzleStratsUsed other) {
        usedNeedNumber = usedNeedNumber || other.usedNeedNumber;
        usedSetClaim = usedSetClaim || other.usedSetClaim;
        usedOnlyNum = usedOnlyNum || other.usedOnlyNum;
        usedNakedNumbers = usedNakedNumbers || other.usedNakedNumbers;
        usedHiddenNumbers = usedHiddenNumbers || other.usedHiddenNumbers;
        usedXWing = usedXWing || other.usedXWing;
        usedColorChains = usedColorChains || other.usedColorChains;
        usedYWing = usedYWing || other.usedYWing;
        usedXCycle = usedXCycle || other.usedXCycle;
        usedBackTracking = usedBackTracking || other.usedBackTracking;
    }

    public String stratsToString() {
        String result = "";
        result += "usedNeedNumber: ";
        if (usedNeedNumber) {
            result += "true ";
        }
        else {
            result += "false ";
        }
        result += "SetClaim: ";
        if (usedSetClaim) {
            result += "true ";
        }
        else {
            result += "false ";
        }
        result += "OnlyNum: ";
        if (usedOnlyNum) {
            result += "true ";
        }
        else {
            result += "false ";
        }
        result += "NakedNumbs: ";
        if (usedNakedNumbers) {
            result += "true ";
        }
        else {
            result += "false ";
        }
        result += "HiddenNums: ";
        if (usedHiddenNumbers) {
            result += "true ";
        }
        else {
            result += "false ";
        }
        result += "XWing: ";
        if (usedXWing) {
            result += "true ";
        }
        else {
            result += "false ";
        }
        result += "ColorChains: ";
        if (usedColorChains) {
            result += "true ";
        }
        else {
            result += "false ";
        }
        result += "YWing: ";
        if (usedYWing) {
            result += "true ";
        }
        else {
            result += "false ";
        }
        result += "XCycle: ";
        if (usedXCycle) {
            result += "true ";
        }
        else {
            result += "false ";
        }
        result += "BackTracking: ";
        if (usedBackTracking) {
            result += "true ";
        }
        else {
            result += "false ";
        }
        return result;
    }

    public boolean harderThan(PuzzleStratsUsed other) {
        if (usedBackTracking != other.usedBackTracking) {
            return usedBackTracking;
        }
        if (usedXCycle != other.usedXCycle) {
            return usedXCycle;
        }
        if (usedYWing != other.usedYWing) {
            return usedYWing;
        }
        if (usedColorChains != other.usedColorChains) {
            return usedColorChains;
        }
        if (usedXWing != other.usedXWing) {
            return usedXWing;
        }
        if (usedHiddenNumbers != other.usedHiddenNumbers) {
            return usedHiddenNumbers;
        }
        if (usedNakedNumbers != other.usedNakedNumbers) {
            return usedNakedNumbers;
        }
        if (usedOnlyNum != other.usedOnlyNum) {
            return usedOnlyNum;
        }
        if (usedSetClaim != other.usedSetClaim) {
            return usedSetClaim;
        }
        if (usedNeedNumber != other.usedNeedNumber) {
            return usedNeedNumber;
        }
        return false;
    }

    public boolean atLeastAsHardAs(PuzzleStratsUsed other) {
        if (usedBackTracking != other.usedBackTracking) {
            return usedBackTracking;
        }
        if (usedXCycle != other.usedXCycle) {
            return usedXCycle;
        }
        if (usedYWing != other.usedYWing) {
            return usedYWing;
        }
        if (usedColorChains != other.usedColorChains) {
            return usedColorChains;
        }
        if (usedXWing != other.usedXWing) {
            return usedXWing;
        }
        if (usedHiddenNumbers != other.usedHiddenNumbers) {
            return usedHiddenNumbers;
        }
        if (usedNakedNumbers != other.usedNakedNumbers) {
            return usedNakedNumbers;
        }
        if (usedOnlyNum != other.usedOnlyNum) {
            return usedOnlyNum;
        }
        if (usedSetClaim != other.usedSetClaim) {
            return usedSetClaim;
        }
        if (usedNeedNumber != other.usedNeedNumber) {
            return usedNeedNumber;
        }
        return true;
    }

    public boolean harderThan(String strat) {
        if (usedBackTracking) {
            return true;
        }
        else if (strat.equals("XCycle")) {
            return false;
        }
        else if (strat.equals("YWing")) {
            return usedXCycle;
        }
        else if (strat.equals("ColorChains")) {
            return usedYWing;
        }
        else if (strat.equals("XWing")) {
            return usedColorChains;
        }
        else if (strat.equals("HiddenNumbers")) {
            return usedXWing;
        }
        else if (strat.equals("NakedNumbers")) {
            return usedXWing || usedHiddenNumbers;
        }
        else if (strat.equals("OnlyNumber")) {
            return usedXWing || usedHiddenNumbers || usedNakedNumbers;
        }
        else if (strat.equals("SetClaim")) {
            return usedXWing || usedHiddenNumbers || usedNakedNumbers || usedOnlyNum;
        }
        else if (strat.equals("SetNeedsNumber")) {
            return usedXWing || usedHiddenNumbers || usedNakedNumbers || usedSetClaim;
        }
        else {
            Log.e("HEY", "strat is not an actual strategy it is: " + strat);
            return true;
        }
    }

    public void setEqualTo(PuzzleStratsUsed strats) {
        usedNeedNumber = strats.usedNeedNumber;
        usedSetClaim = strats.usedSetClaim;
        usedOnlyNum = strats.usedOnlyNum;
        usedNakedNumbers = strats.usedNakedNumbers;
        usedHiddenNumbers = strats.usedHiddenNumbers;
        usedXWing = strats.usedXWing;
        usedColorChains = strats.usedColorChains;
        usedYWing = strats.usedYWing;
        usedXCycle = strats.usedXCycle;
        usedBackTracking = strats.usedBackTracking;
    }


}
