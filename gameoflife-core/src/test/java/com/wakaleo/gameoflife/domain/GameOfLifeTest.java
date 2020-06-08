package com.wakaleo.gameoflife.domain;

import org.junit.Test;

import static com.wakaleo.gameoflife.domain.Universe.seededWith;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class GameOfLifeTest {

    private static final String NEW_LINE = System.getProperty("line.separator");
    public static final String EMPTY_GRID = "..." + NEW_LINE + "..." + NEW_LINE + "..." + NEW_LINE + "";

    @Test
    public void aNewUniverseShouldContainEmptyGrid() {
        Universe theUniverse = new Universe();
        String grid = theUniverse.getGrid();
        assertThat(grid, is(EMPTY_GRID));
    }

    @Test
    public void aNewGridShouldBeEmpty() {
        Grid grid = new Grid();
        assertThat(grid.toString(), is(EMPTY_GRID));
    }

    @Test
    public void oneCellGridWillBeDeadInNextGeneration() {
        String seededGrid = ".*." + NEW_LINE + "..." + NEW_LINE + "..." + NEW_LINE + "";
        String expectedGrid = "..." + NEW_LINE + "..." + NEW_LINE + "..." + NEW_LINE + "";
        Universe theUniverse = new Universe(seededWith(seededGrid));
        theUniverse.spawnsANewGeneration();
        String grid = theUniverse.getGrid();
        assertThat(grid, is(expectedGrid));
    }

    @Test
    public void allCellsGridWillBeDeadInSecondGeneration() {
        String seededGrid = "***" + NEW_LINE + "***" + NEW_LINE + "***" + NEW_LINE + "";
        String expectedNextGeneration = "*.*" + NEW_LINE + "..." + NEW_LINE + "*.*" + NEW_LINE + "";
        String expectedNextNextGeneration = "..." + NEW_LINE + "..." + NEW_LINE + "..." + NEW_LINE + "";
        Universe theUniverse = new Universe(seededWith(seededGrid));
        theUniverse.createNextGeneration();
        String currentGrid = theUniverse.getGrid();
        assertThat(currentGrid, is(expectedNextGeneration));
        theUniverse.createNextGeneration();
        String nextGrid = theUniverse.getGrid();
        assertThat(nextGrid, is(expectedNextNextGeneration));
    }


}