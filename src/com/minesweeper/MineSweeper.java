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
    public String status;
    public boolean gameInProgress;
    int noOfMines;
    Character[][] mineBoard;
    int size;
    static Scanner scanner = new Scanner(System.in);
    List < Indices > indexList = new ArrayList < > ();

    public MineSweeper() {}
    public MineSweeper(int size, int noOfMines) {

        mineBoard = new Character[size][size];
        this.noOfMines = noOfMines;
        this.size = size;
    }

    @Override
    public Character[][] createMineField(int size) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                mineBoard[i][j] = 'X';
            }
        }
        return mineBoard;
    }

    @Override
    public int addMinesRandomly() {
        int minesCount = 0;
        Random random = new Random();
        while (minesCount < noOfMines) {
            int randomRow = random.nextInt(size);
            int randomCol = random.nextInt(size);
            //check if its not already used
            if (mineBoard[randomRow][randomCol] != 'M') {
                mineBoard[randomRow][randomCol] = 'M';
                minesCount++;
                indexList.add(new Indices(randomRow, randomCol));
            }
        }
        return minesCount;
    }

    @Override
    public boolean playGame(int row, int column, Character[][] mineBoard) {
        if (mineBoard[row][column] == '.')
            System.out.println("Mine is already uncovered");

        if (mineBoard[row][column] == 'X') {
            mineBoard[row][column] = '.';
            if (checkForWinningState(mineBoard)) {
                status = "You won the game. Game Over.";
                gameInProgress = false;
                System.out.println(status);
                displayMineBoard(gameInProgress);
                System.exit(1);
            }
        }

        if (mineBoard[row][column] == 'M') {
            mineBoard[row][column] = '*';
            status = "You lost the game. Game Over.";
            gameInProgress = false;
            System.out.println(status);
            displayMineBoard(gameInProgress);
            System.exit(1);
        }
        return true;
    }

    public boolean checkForWinningState(Character[][] mineBoard) {
        int count = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (mineBoard[i][j] == '.')
                    count++;
            }
        }
        //game is won only when mines are covered and everything else uncovered i.e. having period symbol
        return (size * size - count == noOfMines);
    }

    private void displayMineBoard(boolean gameInProgress) {
        System.out.println(gameInProgress);
        System.out.println();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if(gameInProgress) {
                    if(mineBoard[i][j] == 'M')
                        System.out.print('X' + " ");
                    else
                        System.out.print(mineBoard[i][j] + " ");
                }  else  {
                    System.out.print(mineBoard[i][j] + " ");
                }
            }
            System.out.println();
        }
    }
/*
    private void displayTrueMineBoard() {
        System.out.println();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {

            }
            System.out.println();
        }
    }*/

    public boolean validateInput(int number) throws InvalidNumberException {
        if (number < 1) {
            throw new InvalidNumberException("Number can't be less than 0");
        }
        return true;
    }

    public boolean validateInputMines(int noOfMines, int gridSize) throws InvalidMineSizeException {
        if (noOfMines > gridSize * gridSize) {
            throw new InvalidMineSizeException("Number of mines can't be greater than the number of cells");
        }
        return true;
    }

    public static void main(String args[]) {
        MineSweeper mineSweeper = new MineSweeper();
        int numOfMines = 0;
        int size = 0;
        int row, column;
        try {
            System.out.println("Enter the size of the grid");
            size = scanner.nextInt();

            mineSweeper.validateInput(size);

            System.out.println("Enter the number of mines");
            numOfMines = scanner.nextInt();

            mineSweeper.validateInput(numOfMines);
            mineSweeper.validateInputMines(numOfMines, size);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        mineSweeper = new MineSweeper(size, numOfMines);
        mineSweeper.gameInProgress = true;
        mineSweeper.mineBoard = mineSweeper.createMineField(size);
        mineSweeper.addMinesRandomly();
        System.out.println("calling" + mineSweeper.gameInProgress);
        mineSweeper.displayMineBoard(mineSweeper.gameInProgress);
        System.out.println("Lets play the game!!");
        while (true) {
            while (true) {
                System.out.print("Enter row: ");
                row = MineSweeper.scanner.nextInt();
                System.out.print("Enter column: ");
                column = MineSweeper.scanner.nextInt();
                boolean valid = row < size && column < size;
                if (!valid)
                    System.out.println("Error, please enter valid indices");
                else
                    break;
            }
            mineSweeper.playGame(row, column, mineSweeper.mineBoard);
            mineSweeper.displayMineBoard(mineSweeper.gameInProgress);
        }
    }
}

class Indices {
    int row;
    int col;
    Indices(int row, int col) {
        this.row = row;
        this.col = col;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Indices)) {
            return false;
        }
        Indices index = (Indices) obj;
        return row == index.row && col == index.col;
    }
}

class Mine {
        int surroundingMinesCount;
        boolean mine;
        char value;

    Mine(int surroundingMinesCount, boolean mine, char value) {
    this.surroundingMinesCount = surroundingMinesCount;
    this.mine = mine;
    this.value = value;
    }
}