package com.minesweeper;

import java.util.Scanner;

/**
 * Created by ayushij on 1/7/17 
 */
public class DriverProgram {
    public static void main(String args[]) {
            Scanner scanner = new Scanner(System.in);
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
            mineSweeper.createMineField(size);
            mineSweeper.addMinesRandomly();
            mineSweeper.displayMineBoard();

            System.out.println("Lets play the game!!");
            while (true) {
                while (true) {
                    System.out.print("Enter row: ");
                    row = scanner.nextInt();
                    System.out.print("Enter column: ");
                    column = scanner.nextInt();
                    boolean valid = row < size && column < size;
                    if (!valid)
                        System.out.println("Error, please enter valid indices");
                    else
                        break;
                }
                mineSweeper.playGame(row, column);
            }
    }
}
