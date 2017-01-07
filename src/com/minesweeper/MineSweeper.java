package com.minesweeper;
import java.util.*;
/**
 * Created by ayushij on 12/29/16.
 *
 * Symbols: X - denotes covered untouched cell
 *          . - denotes uncovered empty cell
 *          * - denotes blasted mine
 */
public class MineSweeper implements IMineSweeper {

    public MineSweeperBeanClass mineBean = new MineSweeperBeanClass();
    public MineSweeper() {}
    public MineSweeper(int size, int noOfMines) {
        mineBean.setSize(size);
        mineBean.setNoOfMines(noOfMines);
        mineBean.createMineBoard(new Mine[size][size]);
        mineBean.setNoOfUncoveredCells(0);
        mineBean.setGameStatus("progress");
    }

    @Override
    public void createMineField(int size) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                mineBean.setMineBoard(i, j, new Mine(0, true));
            }
        }
    }

    @Override
    public int addMinesRandomly() {
        int minesCount = 0;
        Random random = new Random();
        Mine mine;
        while (minesCount < mineBean.getNoOfMines()) {
            int randomRow = random.nextInt(mineBean.getSize());
            int randomCol = random.nextInt(mineBean.getSize());
            mine = mineBean.getMineBoard()[randomRow][randomCol];
            //check if its not already used
            if (mine.surroundingMinesCount != -1) {
                mine.surroundingMinesCount = -1;
                mineBean.setMineBoard(randomRow, randomCol, mine); //-1 represents its a mine
                setNeighbors(randomRow, randomCol);
                minesCount++;
            }
        }
        return minesCount;
    }


    public boolean isValidCell(int row, int col, boolean uncoveringCells) {
        // if array index out of bounds, return false
        if (row < 0 || row >= mineBean.getSize() || col < 0 || col >= mineBean.getSize())
            return false;
        //if this method is calling from setNeighbors, uncoveringCells is false
        if (!uncoveringCells) {
            return mineBean.getMineBoard()[row][col].surroundingMinesCount != -1;
        } else { //if uncovering cells, validity condition includes that if this cell is a mine, dont uncover neighboring cells
            return (mineBean.getMineBoard()[row][col].covered == true && mineBean.getMineBoard()[row][col].surroundingMinesCount != -1);
        }
    }

    public void setNeighbors(int row, int col) {
        boolean uncoveringCells = false;
        //if any of the neighbors is a mine, we do not set that cell's surroundingMinesCount property
        if (isValidCell(row - 1, col - 1, uncoveringCells))
            mineBean.getMineBoard()[row - 1][col - 1].surroundingMinesCount += 1;
        if (isValidCell(row - 1, col, uncoveringCells))
            mineBean.getMineBoard()[row - 1][col].surroundingMinesCount += 1;
        if (isValidCell(row - 1, col + 1, uncoveringCells))
            mineBean.getMineBoard()[row - 1][col + 1].surroundingMinesCount += 1;
        if (isValidCell(row, col - 1, uncoveringCells))
            mineBean.getMineBoard()[row][col - 1].surroundingMinesCount += 1;
        if (isValidCell(row, col + 1, uncoveringCells))
            mineBean.getMineBoard()[row][col + 1].surroundingMinesCount += 1;
        if (isValidCell(row + 1, col - 1, uncoveringCells))
            mineBean.getMineBoard()[row + 1][col - 1].surroundingMinesCount += 1;
        if (isValidCell(row + 1, col, uncoveringCells))
            mineBean.getMineBoard()[row + 1][col].surroundingMinesCount += 1;
        if (isValidCell(row + 1, col + 1, uncoveringCells))
            mineBean.getMineBoard()[row + 1][col + 1].surroundingMinesCount += 1;
    }

    public void uncoverNeighbors(int row, int col) {
        boolean uncoveringCells = true;
        Queue < Index > neighbors = new LinkedList < > ();
        Index index = new Index(row, col);
        neighbors.add(index);
        while (!neighbors.isEmpty()) {
            Index currentIndex = neighbors.poll();
            row = currentIndex.row;
            col = currentIndex.col;
            if (mineBean.getMineBoard()[row][col].covered != false) {
                mineBean.setNoOfUncoveredCells(mineBean.getNoOfUncoveredCells() + 1);
                mineBean.getMineBoard()[row][col].covered = false;
                if (mineBean.getMineBoard()[row][col].surroundingMinesCount > 0) {
                    continue;
                }
                if (isValidCell(row - 1, col, uncoveringCells))
                    neighbors.add(new Index(row - 1, col));
                if (isValidCell(row + 1, col, uncoveringCells))
                    neighbors.add(new Index(row + 1, col));
                if (isValidCell(row, col - 1, uncoveringCells))
                    neighbors.add(new Index(row, col - 1));
                if (isValidCell(row, col + 1, uncoveringCells))
                    neighbors.add(new Index(row, col + 1));
                if (isValidCell(row - 1, col - 1, uncoveringCells))
                    neighbors.add(new Index(row - 1, col - 1));
                if (isValidCell(row + 1, col + 1, uncoveringCells))
                    neighbors.add(new Index(row + 1, col + 1));
                if (isValidCell(row - 1, col + 1, uncoveringCells))
                    neighbors.add(new Index(row - 1, col + 1));
                if (isValidCell(row + 1, col - 1, uncoveringCells))
                    neighbors.add(new Index(row + 1, col - 1));
            }
        }
    }
    @Override
    public boolean playGame(int row, int column) {
        if (mineBean.getMineBoard()[row][column].covered == false) {
            System.out.println("Mine is already uncovered");
        } else {
            if (mineBean.getMineBoard()[row][column].surroundingMinesCount == -1) {
                mineBean.setGameStatus("lost");
                System.out.println("You lost the game. Game Over.");
            } else {
                if (mineBean.getMineBoard()[row][column].surroundingMinesCount == 0) {
                    uncoverNeighbors(row, column);
                } else {
                    mineBean.getMineBoard()[row][column].covered = false;
                    mineBean.setNoOfUncoveredCells(mineBean.getNoOfUncoveredCells() + 1);
                }
                if (isWinningState()) {
                    mineBean.setGameStatus("won");
                    System.out.println("You won the game. Game Over.");
                }
            }
        }
        displayMineBoard();
        return true;
    }

    public boolean isWinningState() {
        return (mineBean.getNoOfUncoveredCells() == Math.pow(mineBean.getSize(), 2) - mineBean.getNoOfMines());
    }

    public void displayMineBoard() {
        System.out.println();
        if (mineBean.getGameStatus().equalsIgnoreCase("progress")) {
            for (int i = 0; i < mineBean.getSize(); i++) {
                for (int j = 0; j < mineBean.getSize(); j++) {
                    if (!mineBean.getMineBoard()[i][j].covered && mineBean.getMineBoard()[i][j].surroundingMinesCount != 0)
                        System.out.print(mineBean.getMineBoard()[i][j].surroundingMinesCount + " ");
                    else if (!mineBean.getMineBoard()[i][j].covered && mineBean.getMineBoard()[i][j].surroundingMinesCount == 0)
                        System.out.print("." + " ");
                    else
                        System.out.print("X" + " ");
                }
                System.out.println();
            }
        } else {
            if (mineBean.getGameStatus().equalsIgnoreCase("won")) { //reveal everything on the board
                for (int i = 0; i < mineBean.getSize(); i++) {
                    for (int j = 0; j < mineBean.getSize(); j++) {
                        if (mineBean.getMineBoard()[i][j].surroundingMinesCount == -1)
                            System.out.print("*" + " ");
                        else
                            System.out.print(mineBean.getMineBoard()[i][j].surroundingMinesCount + " ");
                    }
                    System.out.println();
                }
            } else if (mineBean.getGameStatus().equalsIgnoreCase("lost")) { //show only what's uncovered and mines
                for (int i = 0; i < mineBean.getSize(); i++) {
                    for (int j = 0; j < mineBean.getSize(); j++) {
                        if (mineBean.getMineBoard()[i][j].surroundingMinesCount == -1)
                            System.out.print("*" + " ");
                        else {
                            if (mineBean.getMineBoard()[i][j].covered == false)
                                System.out.print(mineBean.getMineBoard()[i][j].surroundingMinesCount + " ");
                            else
                                System.out.print("X" + " ");
                        }
                    }
                    System.out.println();
                }
            }
            System.exit(1);
        }

    }

    public void validateInput(int number) throws InvalidNumberException {
        if (number < 1)
            throw new InvalidNumberException("Number can't be less than 0");
    }

    public void validateInputMines(int noOfMines, int gridSize) throws InvalidMineSizeException {
        if (noOfMines > gridSize * gridSize)
            throw new InvalidMineSizeException("Number of mines can't be greater than the number of cells");
    }
}