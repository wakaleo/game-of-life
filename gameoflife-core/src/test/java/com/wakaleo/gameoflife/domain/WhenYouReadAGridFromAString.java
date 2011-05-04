package com.wakaleo.gameoflife.domain;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static com.wakaleo.gameoflife.domain.Cell.*;

import org.junit.Test;

import com.wakaleo.gameoflife.domain.Cell;
import com.wakaleo.gameoflife.domain.GridReader;

public class WhenYouReadAGridFromAString {

    @Test
    public void shouldBeAbleToReadAnEmptyGridOfCellsFromAnEmptyString() {
        String gridContents = "";

        Cell[][] expectedCells = {{}};
        
        GridReader gridReader = new GridReader();
        Cell[][] loadedCells = gridReader.loadFrom(gridContents);
        
        assertThat(loadedCells, is(expectedCells));
    }
    
    @Test
    public void shouldBeAbleToReadAGridContainingASingleCellFromAString() {
        String gridContents = "*";

        Cell[][] expectedCells = {{LIVE_CELL}};
        
        GridReader gridReader = new GridReader();
        Cell[][] loadedCells = gridReader.loadFrom(gridContents);
        
        assertThat(loadedCells, is(expectedCells));
    }

    @Test
    public void shouldBeAbleToReadAGridOfCellsFromAString() {
        String gridContents = "...\n" + 
                              "...\n" +
                              "...";

        Cell[][] expectedCells = {
                {DEAD_CELL, DEAD_CELL, DEAD_CELL},
                {DEAD_CELL, DEAD_CELL, DEAD_CELL}, 
                {DEAD_CELL, DEAD_CELL, DEAD_CELL} 
        };
        
        GridReader gridReader = new GridReader();
        Cell[][] loadedCells = gridReader.loadFrom(gridContents);
        
        assertThat(loadedCells, is(expectedCells));
    }

    @Test(expected=IllegalArgumentException.class)
    public void shouldRefuseIllegalCellCharacters() {
        String gridContents = "...\n" + 
                              ".Z.\n" +
                              "...";

        Cell[][] expectedCells = {
                {DEAD_CELL, DEAD_CELL, DEAD_CELL},
                {DEAD_CELL, DEAD_CELL, DEAD_CELL}, 
                {DEAD_CELL, DEAD_CELL, DEAD_CELL} 
        };
        
        GridReader gridReader = new GridReader();
        Cell[][] loadedCells = gridReader.loadFrom(gridContents);
        
        assertThat(loadedCells, is(expectedCells));
    }

    @Test
    public void shouldBeAbleToReadAGridContainingLiveAndDeadCells() {
        String gridContents = "*..\n" + 
                              ".*.\n" +
                              "..*";

        Cell[][] expectedCells = {
                {LIVE_CELL, DEAD_CELL, DEAD_CELL},
                {DEAD_CELL, LIVE_CELL, DEAD_CELL}, 
                {DEAD_CELL, DEAD_CELL, LIVE_CELL} 
        };
        
        GridReader gridReader = new GridReader();
        Cell[][] loadedCells = gridReader.loadFrom(gridContents);
        
        assertThat(loadedCells, is(expectedCells));
    }

    @Test
    public void shouldBeAbleToReadAnAsymetricalGridContainingLiveAndDeadCells() {
        String gridContents = "....\n" + 
                              "**..\n" +
                              "..*.";

        Cell[][] expectedCells = {
                {DEAD_CELL, DEAD_CELL, DEAD_CELL, DEAD_CELL},
                {LIVE_CELL, LIVE_CELL, DEAD_CELL, DEAD_CELL}, 
                {DEAD_CELL, DEAD_CELL, LIVE_CELL, DEAD_CELL} 
        };
        
        GridReader gridReader = new GridReader();
        Cell[][] loadedCells = gridReader.loadFrom(gridContents);
        
        assertThat(loadedCells, is(expectedCells));
    }    
    
    @Test
    public void shouldBeAbleToReadALargerGrid() {
        String gridContents = "......\n" + 
                              "**....\n" +
                              "..*...\n" +
                              "..*...\n" +
                              "..*...\n" +
                              "..*...";

        Cell[][] expectedCells = {
                {DEAD_CELL, DEAD_CELL, DEAD_CELL,DEAD_CELL, DEAD_CELL, DEAD_CELL},
                {LIVE_CELL, LIVE_CELL, DEAD_CELL,DEAD_CELL, DEAD_CELL, DEAD_CELL}, 
                {DEAD_CELL, DEAD_CELL, LIVE_CELL,DEAD_CELL, DEAD_CELL, DEAD_CELL},
                {DEAD_CELL, DEAD_CELL, LIVE_CELL,DEAD_CELL, DEAD_CELL, DEAD_CELL},
                {DEAD_CELL, DEAD_CELL, LIVE_CELL,DEAD_CELL, DEAD_CELL, DEAD_CELL}, 
                {DEAD_CELL, DEAD_CELL, LIVE_CELL,DEAD_CELL, DEAD_CELL, DEAD_CELL}, 
        };
        
        GridReader gridReader = new GridReader();
        Cell[][] loadedCells = gridReader.loadFrom(gridContents);
        
        assertThat(loadedCells, is(expectedCells));
    }        
}