package com.minesweeper;

/**
 * Created by ayushij on 1/6/17.
 */

public class Mine {
    int surroundingMinesCount; //shows how many mines surround this cell
    boolean covered;    //whether this cell has been revealed or not

    public Mine(int surroundingMinesCount, boolean covered) {
        this.surroundingMinesCount = surroundingMinesCount;
        this.covered = covered;
    }
}