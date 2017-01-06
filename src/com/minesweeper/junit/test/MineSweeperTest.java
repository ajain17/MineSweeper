package com.minesweeper.junit.test;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import com.minesweeper.MineSweeper;
import org.junit.contrib.java.lang.system.Assertion;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;
import org.junit.rules.ExpectedException;
import org.junit.*;
import com.minesweeper.InvalidNumberException;
import com.minesweeper.InvalidMineSizeException;

/**
 * Created by ayushij on 12/29/16.
 */
public class MineSweeperTest {

    Character[][] mineboard = { { 'X', 'X' }, { 'X', 'X' } };
    public int size = 2;
    public int mines = 2;

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    @Rule
    public final ExpectedSystemExit exit = ExpectedSystemExit.none();

    @Test
    public void createMineField() {
        System.out.println("@Test - create mine field");
        MineSweeper tester = new MineSweeper(size, mines);
        assertArrayEquals("should create mine field with all X initially", mineboard, tester.createMineField(size));
    }

    @Test
    public void addMinesRandomly() {
        System.out.println("@Test - add mines randomly");
        MineSweeper tester = new MineSweeper(size, mines);
        tester.createMineField(size);
        assertEquals("should add mines equal to number of mines", mines, tester.addMinesRandomly());
    }
    @Test
    public void playGame() {
        System.out.println("@Test - play game");
        MineSweeper tester = new MineSweeper(size, mines);
        Character[][] mineboard1 = { { 'M', '.' }, { 'M', 'X' } };

        exit.expectSystemExitWithStatus(1);
        exit.checkAssertionAfterwards(new Assertion() {
            public void checkAssertion() throws Exception {
                assertEquals("You lost the game. Game Over.", tester.status);
            }
        });
        tester.playGame(0, 0, mineboard1);

        exit.expectSystemExitWithStatus(1);
        exit.checkAssertionAfterwards(new Assertion() {
            public void checkAssertion() throws Exception {
                assertEquals("You won the game. Game Over.", tester.status);
            }
        });
        tester.playGame(1, 1, mineboard1);

        Character[][] mineboard2 = { { 'M', 'X' }, { 'M', 'X' } };
        assertEquals("should return true", true, tester.playGame(0, 1, mineboard2));
    }

    @Test
    public void checkForWinningState() {
        System.out.println("@Test - check for winning state");
        MineSweeper tester = new MineSweeper(size, mines);
        Character[][] mineboard = { { 'M', '.' }, { 'M', '.' } };
        boolean result = tester.checkForWinningState(mineboard);
        assertEquals("when only mines are left uncovered, user wins", true, result);
        Character[][] mineboard1 = { { 'M', '.' }, { 'M', 'X' } };
        boolean result1 = tester.checkForWinningState(mineboard1);
        assertEquals("when non mines are left uncovered, user can't win", false, result1);
    }

    @Test(expected = InvalidNumberException.class)
    public void validateInput() throws InvalidNumberException {
        System.out.println("@Test - validate input for grid size and number of mines ");
        MineSweeper tester = new MineSweeper();
        int size = 0;
        tester.validateInput(size);
        thrown.expect(InvalidNumberException.class);
        thrown.expectMessage("Number can't be less than 0");
        assertEquals("should return true for valid input", true, tester.validateInput(5));

    }

    @Test(expected = InvalidMineSizeException.class)
    public void validateInputMines() throws InvalidMineSizeException {
        System.out.println("@Test - validate user input for number of mines");
        MineSweeper tester = new MineSweeper();
        int size = 2;
        int numOfMines = 5;
        tester.validateInputMines(numOfMines, size);
        thrown.expect(InvalidMineSizeException.class);
        thrown.expectMessage("Number of mines can't be greater than number of cells");
        assertEquals("should return true for valid input", true, tester.validateInputMines(2, 3));
    }

}