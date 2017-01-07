package com.minesweeper;

/**
 * Created by ayushij on 1/6/17.
 */
public class MineSweeperBeanClass {
    private String gameStatus;
    private int noOfMines; //total number of mines
    private Mine[][] mineBoard;
    private int noOfUncoveredCells;
    private int size;

    public String getGameStatus() {
        return gameStatus;
    }

    public void createMineBoard(Mine[][] mineBoard) {
        this.mineBoard = mineBoard;
    }
    public void setGameStatus(String gameStatus) {
        this.gameStatus = gameStatus;
    }

    public int getNoOfMines() {
        return noOfMines;
    }

    public void setNoOfMines(int noOfMines) {
        this.noOfMines = noOfMines;
    }

    public Mine[][] getMineBoard() {
        return mineBoard;
    }

    public void setMineBoard(int row, int col, Mine value) {
        this.mineBoard[row][col] = value;
    }

    public int getNoOfUncoveredCells() {
        return noOfUncoveredCells;
    }

    public void setNoOfUncoveredCells(int noOfUncoveredCells) {
        this.noOfUncoveredCells = noOfUncoveredCells;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}