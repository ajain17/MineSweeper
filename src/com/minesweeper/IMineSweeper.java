package com.minesweeper;

/**
 * Created by ayushij on 12/29/16.
 */

public interface IMineSweeper {
    void createMineField(int size);
    int addMinesRandomly();
    boolean playGame(int row, int column);
    boolean isWinningState();
}