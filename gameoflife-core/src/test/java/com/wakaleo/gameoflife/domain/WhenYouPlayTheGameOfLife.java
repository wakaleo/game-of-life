package com.wakaleo.gameoflife.domain;

import org.junit.Test;

import com.wakaleo.gameoflife.domain.Universe;

import static com.wakaleo.gameoflife.domain.Universe.seededWith;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class WhenYouPlayTheGameOfLife {

    @Test
    public void aDeadCellWithNoNeighboursShouldRemainDeadInTheNextGeneration() {
        String initialGrid = "...\n" +
                             "...\n" +
                             "...";

        String expectedNextGrid = "...\n" +
                                  "...\n" +
                                  "...\n";

        Universe theUniverse = new Universe(seededWith(initialGrid));
        theUniverse.createNextGeneration();

        String nextGrid = theUniverse.getGrid();
        assertThat(nextGrid, is(expectedNextGrid));
    }

    @Test
    public void aDeadCellWithOneLiveNeighbourShouldRemainDeadInTheNextGeneration() {
        String initialGrid = "...\n" +
                             ".*.\n" +
                             "...";

        String expectedNextGrid = "...\n" +
                                  "...\n" +
                                  "...\n";

        Universe theUniverse = new Universe(seededWith(initialGrid));
        theUniverse.createNextGeneration();

        String nextGrid = theUniverse.getGrid();
        assertThat(nextGrid, is(expectedNextGrid));
    }

    @Test
    public void liveCellaWith2or3iveNeighbourShouldRemainAliveInTheNextGeneration() {
        String initialGrid = "**.\n" +
                             "**.\n" +
                             "...";

        String expectedNextGrid = "**.\n" +
                                  "**.\n" +
                                  "...\n";

        Universe theUniverse = new Universe(seededWith(initialGrid));
        theUniverse.createNextGeneration();

        String nextGrid = theUniverse.getGrid();
        assertThat(nextGrid, is(expectedNextGrid));
    }


    @Test
    public void aLiveCellWithExactlyTwoLiveNeighbourShouldRemainAliveInTheNextGeneration() {
        String initialGrid = "...\n" +
                             "***\n" +
                             "...\n";

        String expectedNextGrid = ".*.\n" +
                                  ".*.\n" +
                                  ".*.\n";

        Universe theUniverse = new Universe(seededWith(initialGrid));
        theUniverse.createNextGeneration();

        String nextGrid = theUniverse.getGrid();
        assertThat(nextGrid, is(expectedNextGrid));
    }



    @Test
    public void aCellWithNoNeighboursShouldDieInTheNextGeneration() {
        String initialGrid = "...\n" +
                             "...\n" +
                             "...\n";

        String expectedNextGrid = "...\n" +
                                  "...\n" +
                                  "...\n";

        Universe theUniverse = new Universe(seededWith(initialGrid));
        theUniverse.createNextGeneration();

        String nextGrid = theUniverse.getGrid();
        assertThat(nextGrid, is(expectedNextGrid));
    }
}
