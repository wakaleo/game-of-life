package com.wakaleo.gameoflife.domain;

import org.junit.Test;

import com.wakaleo.gameoflife.domain.Universe;

import static com.wakaleo.gameoflife.domain.Universe.seededWith;
import static com.wakaleo.gameoflife.domain.Cell.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class WhenYouCreateANewUniverse {

    private static final String NEW_LINE = System.getProperty("line.separator");

    public static final String EMPTY_GRID = "..." + NEW_LINE + "..." + NEW_LINE + "..." + NEW_LINE + "";

    @Test
    public void aNewUniverseShouldContainOnlyDeadCells() {
        Universe theUniverse = new Universe();
        String currentGrid = theUniverse.getGrid();
        assertThat(currentGrid, is(EMPTY_GRID));
    }

    @Test
    public void aUniverseSeededWithAnEmpyGridContentWillContainAnEmptyGrid() {

        String seededGrid = "..." + NEW_LINE + "..." + NEW_LINE + "..." + NEW_LINE + "";

        Universe theUniverse = new Universe(seededWith(seededGrid));
        String currentGrid = theUniverse.getGrid();
        assertThat(currentGrid, is(seededGrid));
    }

    @Test
    public void aUniverseCanBeInitializedWithAnyDimension() {
        String expectedGrid = "....." + NEW_LINE + "....." + NEW_LINE + "....." + NEW_LINE + "....." + NEW_LINE + "";

        Universe theUniverse = new Universe(4, 5);
        String currentGrid = theUniverse.getGrid();
        assertThat(currentGrid, is(expectedGrid));

    }

    @Test
    public void aUniverseSeededWithAGridContainingASingleLiveCellContentWillSpawnAnEmptyGrid() {

        String seededGrid = "..." + NEW_LINE + ".*." + NEW_LINE + "..." + NEW_LINE + "";

        String expectedGrid = "..." + NEW_LINE + "..." + NEW_LINE + "..." + NEW_LINE + "";

        Universe theUniverse = new Universe(seededWith(seededGrid));
        theUniverse.spawnsANewGeneration();
        String currentGrid = theUniverse.getGrid();
        assertThat(currentGrid, is(expectedGrid));
    }

    @Test
    public void aUniverseSeededWithAGridWithLivingCellsContentWillContainThatGrid() {

        String seededGrid = "*.." + NEW_LINE + ".*." + NEW_LINE + "..*" + NEW_LINE + "";

        Universe theUniverse = new Universe(seededWith(seededGrid));
        String currentGrid = theUniverse.getGrid();
        assertThat(currentGrid, is(seededGrid));
    }

    @Test
    public void aUniverseSpawnsANewGridInTheNextGeneration() {

        String seededGrid = "..." + NEW_LINE + "***" + NEW_LINE + "..." + NEW_LINE + "";

        String expectedNextGeneration = ".*." + NEW_LINE + ".*." + NEW_LINE + ".*." + NEW_LINE + "";

        Universe theUniverse = new Universe(seededWith(seededGrid));
        theUniverse.createNextGeneration();
        String currentGrid = theUniverse.getGrid();
        assertThat(currentGrid, is(expectedNextGeneration));
    }

    @Test
    public void aUserCanAssignALiveCellAtAGivenPointInTheGrid() {
        String seededGrid = "..." + NEW_LINE + "..." + NEW_LINE + "..." + NEW_LINE + "";

        String expectedState = "*.." + NEW_LINE + "*.." + NEW_LINE + ".*." + NEW_LINE + "";

        Universe theUniverse = new Universe(seededWith(seededGrid));
        theUniverse.setLiveCellAt(0, 0);
        theUniverse.setLiveCellAt(1, 0);
        theUniverse.setLiveCellAt(2, 1);

        assertThat(theUniverse.getGrid(), is(expectedState));
    }

    @Test
    public void aUserCanAssignADeadCellAtAGivenPointInTheGrid() {
        String seededGrid = "***" + NEW_LINE + "***" + NEW_LINE + "***" + NEW_LINE + "";

        String expectedState = "*.*" + NEW_LINE + "***" + NEW_LINE + "***" + NEW_LINE + "";

        Universe theUniverse = new Universe(seededWith(seededGrid));
        theUniverse.setDeadCellAt(0, 1);
        assertThat(theUniverse.getGrid(), is(expectedState));
    }


    @Test
    public void aUserCanReadALiveCellValueAtAGivenPointInTheGrid() {
        String seededGrid = "*.." + NEW_LINE + "*.." + NEW_LINE + ".*." + NEW_LINE + "";

        Universe theUniverse = new Universe(seededWith(seededGrid));

        assertThat(theUniverse.getCellAt(0, 0), is(LIVE_CELL));
        assertThat(theUniverse.getCellAt(1, 0), is(LIVE_CELL));
        assertThat(theUniverse.getCellAt(2, 1), is(LIVE_CELL));
    }

    @Test
    public void aUserCanReadADeadCellValueAtAGivenPointInTheGrid() {
        String seededGrid = "*.." + NEW_LINE + "*.." + NEW_LINE + ".*." + NEW_LINE + "";

        Universe theUniverse = new Universe(seededWith(seededGrid));

        assertThat(theUniverse.getCellAt(0, 1), is(DEAD_CELL));
        assertThat(theUniverse.getCellAt(1, 1), is(DEAD_CELL));
    }

    @Test
    public void aUserCanObtainTheGridContentsAsAnArrayOfCells() {
        String seededGrid = "*.." + NEW_LINE + "*.." + NEW_LINE + ".*." + NEW_LINE + "";
        Universe theUniverse = new Universe(seededWith(seededGrid));

        Cell[][] expectedCells = new Cell[][]{
                {LIVE_CELL, DEAD_CELL, DEAD_CELL},
                {LIVE_CELL, DEAD_CELL, DEAD_CELL},
                {DEAD_CELL, LIVE_CELL, DEAD_CELL},
        };

        assertThat(theUniverse.getCells(), is(expectedCells));
    }


}
