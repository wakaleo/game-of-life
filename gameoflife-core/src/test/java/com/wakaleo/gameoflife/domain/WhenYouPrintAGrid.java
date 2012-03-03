package com.wakaleo.gameoflife.domain;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static com.wakaleo.gameoflife.domain.Cell.*;

import org.junit.Test;

import com.wakaleo.gameoflife.domain.Cell;
import com.wakaleo.gameoflife.domain.GridWriter;

public class WhenYouPrintAGrid {

	private final String NEW_LINE = System.getProperty("line.separator");

    @Test
    public void shouldBeAbleToReadAGridOfCellsFromAString() {
        Cell[][] gridContents = {
                {DEAD_CELL, DEAD_CELL, DEAD_CELL},
                {DEAD_CELL, DEAD_CELL, DEAD_CELL}, 
                {DEAD_CELL, DEAD_CELL, DEAD_CELL} 
        };
        
        String expectedPrintedGrid = "..." + NEW_LINE + 
                                     "..." + NEW_LINE +
                                     "..." + NEW_LINE + "";
        
        GridWriter gridWriter = new GridWriter();
        String printedGrid = gridWriter.convertToString(gridContents);
        assertThat(printedGrid, is(expectedPrintedGrid));
    }

    @Test
    public void shouldBeAbleToReadAStringGridContainingLiveAndDeadCells() {
        Cell[][] gridContents = {
                {LIVE_CELL, DEAD_CELL, DEAD_CELL},
                {DEAD_CELL, LIVE_CELL, DEAD_CELL}, 
                {DEAD_CELL, LIVE_CELL, DEAD_CELL} 
        };
        
        String expectedPrintedGrid = "*.." + NEW_LINE + 
                                     ".*." + NEW_LINE +
                                     ".*." + NEW_LINE + "";
        
        GridWriter gridWriter = new GridWriter();
        String printedGrid = gridWriter.convertToString(gridContents);
        assertThat(printedGrid, is(expectedPrintedGrid));
    }

    @Test
    public void shouldBeAbleToReadALargeStringGridContainingLiveAndDeadCells() {
        Cell[][] gridContents = {
                {LIVE_CELL, DEAD_CELL, DEAD_CELL, LIVE_CELL, DEAD_CELL, DEAD_CELL,LIVE_CELL, DEAD_CELL, DEAD_CELL, LIVE_CELL, DEAD_CELL, DEAD_CELL},
                {DEAD_CELL, LIVE_CELL, DEAD_CELL, DEAD_CELL, LIVE_CELL, DEAD_CELL,DEAD_CELL, LIVE_CELL, DEAD_CELL,DEAD_CELL, LIVE_CELL, DEAD_CELL}, 
                {DEAD_CELL, LIVE_CELL, DEAD_CELL, DEAD_CELL, LIVE_CELL, DEAD_CELL, DEAD_CELL, LIVE_CELL, DEAD_CELL, DEAD_CELL, LIVE_CELL, DEAD_CELL},
                {LIVE_CELL, DEAD_CELL, DEAD_CELL, LIVE_CELL, DEAD_CELL, DEAD_CELL,LIVE_CELL, DEAD_CELL, DEAD_CELL, LIVE_CELL, DEAD_CELL, DEAD_CELL},
                {DEAD_CELL, LIVE_CELL, DEAD_CELL, DEAD_CELL, LIVE_CELL, DEAD_CELL,DEAD_CELL, LIVE_CELL, DEAD_CELL,DEAD_CELL, LIVE_CELL, DEAD_CELL}, 
                {DEAD_CELL, LIVE_CELL, DEAD_CELL, DEAD_CELL, LIVE_CELL, DEAD_CELL, DEAD_CELL, LIVE_CELL, DEAD_CELL, DEAD_CELL, LIVE_CELL, DEAD_CELL},
                {LIVE_CELL, DEAD_CELL, DEAD_CELL, LIVE_CELL, DEAD_CELL, DEAD_CELL,LIVE_CELL, DEAD_CELL, DEAD_CELL, LIVE_CELL, DEAD_CELL, DEAD_CELL},
                {DEAD_CELL, LIVE_CELL, DEAD_CELL, DEAD_CELL, LIVE_CELL, DEAD_CELL,DEAD_CELL, LIVE_CELL, DEAD_CELL,DEAD_CELL, LIVE_CELL, DEAD_CELL}, 
                {DEAD_CELL, LIVE_CELL, DEAD_CELL, DEAD_CELL, LIVE_CELL, DEAD_CELL, DEAD_CELL, LIVE_CELL, DEAD_CELL, DEAD_CELL, LIVE_CELL, DEAD_CELL},
                {LIVE_CELL, DEAD_CELL, DEAD_CELL, LIVE_CELL, DEAD_CELL, DEAD_CELL,LIVE_CELL, DEAD_CELL, DEAD_CELL, LIVE_CELL, DEAD_CELL, DEAD_CELL},
                {DEAD_CELL, LIVE_CELL, DEAD_CELL, DEAD_CELL, LIVE_CELL, DEAD_CELL,DEAD_CELL, LIVE_CELL, DEAD_CELL,DEAD_CELL, LIVE_CELL, DEAD_CELL}, 
                {DEAD_CELL, LIVE_CELL, DEAD_CELL, DEAD_CELL, LIVE_CELL, DEAD_CELL, DEAD_CELL, LIVE_CELL, DEAD_CELL, DEAD_CELL, LIVE_CELL, DEAD_CELL},
        };
        
        String expectedPrintedGrid = "*..*..*..*.." + NEW_LINE + 
                                     ".*..*..*..*." + NEW_LINE +
                                     ".*..*..*..*." + NEW_LINE + 
                                     "*..*..*..*.." + NEW_LINE + 
                                     ".*..*..*..*." + NEW_LINE +
                                     ".*..*..*..*." + NEW_LINE +
                                     "*..*..*..*.." + NEW_LINE + 
                                     ".*..*..*..*." + NEW_LINE +
                                     ".*..*..*..*." + NEW_LINE +
                                     "*..*..*..*.." + NEW_LINE + 
                                     ".*..*..*..*." + NEW_LINE +
                                     ".*..*..*..*." + NEW_LINE + "";
        
        GridWriter gridWriter = new GridWriter();
        String printedGrid = gridWriter.convertToString(gridContents);
        assertThat(printedGrid, is(expectedPrintedGrid));
    }
    
}