package com.minesweeper.junit.test;
import static org.junit.Assert.assertEquals;
import com.minesweeper.MineSweeper;
import com.minesweeper.Mine;
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

    Mine initialMine = new Mine(0, true);
    Mine[][] mineboard = {
            {
                    initialMine,
                    initialMine
            },
            {
                    initialMine,
                    initialMine
            }
    };
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
        tester.createMineField(size);
        assertEquals("should create mine field with all X initially", mineboard.length, tester.mineBean.getMineBoard().length);
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
        System.out.println("@Test - test play game winning case");
        Mine mine1 = new Mine(-1, true);
        Mine mine2 = new Mine(1, true);
        Mine mine3 = new Mine(0, true);
        Mine mine4 = new Mine(1, true);
        Mine mine5 = new Mine(1, true);
        Mine mine6 = new Mine(0, true);
        Mine mine7 = new Mine(0, true);
        Mine mine8 = new Mine(0, true);
        Mine mine9 = new Mine(0, true);
        Mine[][] mineboard = {
                {
                        mine1,
                        mine2,
                        mine3
                },
                {
                        mine4,
                        mine5,
                        mine6
                },
                {
                        mine7,
                        mine8,
                        mine9
                }
        };
        MineSweeper tester = new MineSweeper(3, 1);
        tester.mineBean.createMineBoard(mineboard);
        exit.expectSystemExitWithStatus(1);
        exit.checkAssertionAfterwards(new Assertion() {
            public void checkAssertion() throws Exception {
                assertEquals("won", tester.mineBean.getGameStatus());
            }
        });
        tester.playGame(0, 1);
        tester.playGame(0, 1);
        tester.playGame(2, 0);
    }

    @Test
    public void playGameLosing() {
        System.out.println("@Test - test play game losing case");
        Mine mine1 = new Mine(-1, true);
        Mine mine2 = new Mine(1, true);
        Mine mine3 = new Mine(0, true);
        Mine mine4 = new Mine(1, true);
        Mine mine5 = new Mine(1, true);
        Mine mine6 = new Mine(0, true);
        Mine mine7 = new Mine(0, true);
        Mine mine8 = new Mine(0, true);
        Mine mine9 = new Mine(0, true);
        Mine[][] mineboard = {
                {
                        mine1,
                        mine2,
                        mine3
                },
                {
                        mine4,
                        mine5,
                        mine6
                },
                {
                        mine7,
                        mine8,
                        mine9
                }
        };
        MineSweeper tester2 = new MineSweeper(3, 1);
        tester2.mineBean.createMineBoard(mineboard);
        exit.expectSystemExitWithStatus(1);
        exit.checkAssertionAfterwards(new Assertion() {
            public void checkAssertion() throws Exception {
                assertEquals("lost", tester2.mineBean.getGameStatus());
            }
        });
        tester2.playGame(0, 0);
    }

    @Test
    public void checkForWinningState() {
        System.out.println("@Test - check for winning state");
        MineSweeper tester = new MineSweeper(size, mines);
        tester.mineBean.setNoOfUncoveredCells(2);
        tester.mineBean.setSize(2);
        tester.mineBean.setNoOfMines(2);
        assertEquals("should win if only mine cells are left uncovered", true, tester.isWinningState());
        tester.mineBean.setNoOfUncoveredCells(1);

        assertEquals("should not win if non mine cells are uncovered", false, tester.isWinningState());
    }

    @Test(expected = InvalidNumberException.class)
    public void validateInput() throws InvalidNumberException {
        System.out.println("@Test - validate input for grid size and number of mines ");
        MineSweeper tester = new MineSweeper();
        int size = 0;
        tester.validateInput(size);
        thrown.expect(InvalidNumberException.class);
        thrown.expectMessage("Number can't be less than 0");
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
    }
}