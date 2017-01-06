package com.minesweeper;

/**
 * Created by ayushij on 12/29/16.
 */
public interface IMineSweeper {
    Character[][] createMineField(int size);
    int addMinesRandomly();
    boolean playGame(int row, int column, Character[][] mineBoard);
    boolean checkForWinningState(Character[][] mineBoard);
}