package com.minesweeper;

/**
 * Created by ayushij on 1/6/17.
 */
public class Index {
    int row;
    int col;
    public Index(int row, int col) {
        this.row = row;
        this.col = col;
    }

    @Override
    public String toString() {
        return row + "," + col;
    }
}