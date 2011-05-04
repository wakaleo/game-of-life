package com.wakaleo.gameoflife.domain;

import org.junit.Test;
import org.junit.Ignore;

import com.wakaleo.gameoflife.domain.Universe;

import static com.wakaleo.gameoflife.domain.Universe.seededWith;
import static com.wakaleo.gameoflife.domain.Cell.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class WhenYouCreateANewUniverse {

    public static final String EMPTY_GRID = "...\n" + "...\n" + "...\n";

    @Test
    public void aNewUniverseShouldContainOnlyDeadCells() {
        Universe theUniverse = new Universe();
        String currentGrid = theUniverse.getGrid();
        assertThat(currentGrid, is(EMPTY_GRID));
    }

    @Test
    public void aUniverseSeededWithAnEmpyGridContentWillContainAnEmptyGrid() {

        String seededGrid = "...\n" + "...\n" + "...\n";

        Universe theUniverse = new Universe(seededWith(seededGrid));
        String currentGrid = theUniverse.getGrid();
        assertThat(currentGrid, is(seededGrid));
    }

    @Test
    public void aUniverseCanBeInitializedWithAnyDimension() {
        String expectedGrid = ".....\n" + ".....\n" + ".....\n" + ".....\n";

    	Universe theUniverse = new Universe(4,5);
        String currentGrid = theUniverse.getGrid();
        assertThat(currentGrid, is(expectedGrid));
    	
    }
    
    @Test
    public void aUniverseSeededWithAGridContainingASingleLiveCellContentWillSpawnAnEmptyGrid() {

        String seededGrid = "...\n" + ".*.\n" + "...\n";

        String expectedGrid = "...\n" + "...\n" + "...\n";

        Universe theUniverse = new Universe(seededWith(seededGrid));
        theUniverse.spawnsANewGeneration();
        String currentGrid = theUniverse.getGrid();
        assertThat(currentGrid, is(expectedGrid));
    }

    @Test
    public void aUniverseSeededWithAGridWithLivingCellsContentWillContainThatGrid() {

        String seededGrid = "*..\n" + ".*.\n" + "..*\n";

        Universe theUniverse = new Universe(seededWith(seededGrid));
        String currentGrid = theUniverse.getGrid();
        assertThat(currentGrid, is(seededGrid));
    }

    @Test
    public void aUniverseSpawnsANewGridInTheNextGeneration() {

        String seededGrid = "...\n" + "***\n" + "...\n";

        String expectedNextGeneration = ".*.\n" + ".*.\n" + ".*.\n";

        Universe theUniverse = new Universe(seededWith(seededGrid));
        theUniverse.createNextGeneration();
        String currentGrid = theUniverse.getGrid();
        assertThat(currentGrid, is(expectedNextGeneration));
    }

    @Test
    public void aUserCanAssignALiveCellAtAGivenPointInTheGrid() {
        String seededGrid = "...\n...\n...\n";

        String expectedState = "*..\n" + "*..\n" + ".*.\n";

        Universe theUniverse = new Universe(seededWith(seededGrid));
        theUniverse.setLiveCellAt(0, 0);
        theUniverse.setLiveCellAt(1, 0);
        theUniverse.setLiveCellAt(2, 1);
        
        assertThat(theUniverse.getGrid(), is(expectedState));    
    }
    
    @Test
    public void aUserCanAssignADeadCellAtAGivenPointInTheGrid() {
        String seededGrid = "***\n***\n***\n";

        String expectedState = "*.*\n" + "***\n" + "***\n";

        Universe theUniverse = new Universe(seededWith(seededGrid));
        theUniverse.setDeadCellAt(0, 1);
        assertThat(theUniverse.getGrid(), is(expectedState));    
    }
    

    @Test
    public void aUserCanReadALiveCellValueAtAGivenPointInTheGrid() {
        String seededGrid = "*..\n" + "*..\n" + ".*.\n";

        Universe theUniverse = new Universe(seededWith(seededGrid));

        assertThat(theUniverse.getCellAt(0, 0), is(LIVE_CELL));    
        assertThat(theUniverse.getCellAt(1, 0), is(LIVE_CELL));    
        assertThat(theUniverse.getCellAt(2, 1), is(LIVE_CELL));    
    }

    @Test
    public void aUserCanReadADeadCellValueAtAGivenPointInTheGrid() {
        String seededGrid = "*..\n" + "*..\n" + ".*.\n";

        Universe theUniverse = new Universe(seededWith(seededGrid));
        
        assertThat(theUniverse.getCellAt(0, 1), is(DEAD_CELL));    
        assertThat(theUniverse.getCellAt(1, 1), is(DEAD_CELL));    
    }
    
    @Test
    public void aUserCanObtainTheGridContentsAsAnArrayOfCells() {
        String seededGrid = "*..\n" + "*..\n" + ".*.\n";
        Universe theUniverse = new Universe(seededWith(seededGrid));

        Cell[][] expectedCells = new Cell[][] {
                {LIVE_CELL, DEAD_CELL, DEAD_CELL},
                {LIVE_CELL, DEAD_CELL, DEAD_CELL},
                {DEAD_CELL, LIVE_CELL, DEAD_CELL},
        };
        
        assertThat(theUniverse.getCells(), is(expectedCells));    
    }

    
}
