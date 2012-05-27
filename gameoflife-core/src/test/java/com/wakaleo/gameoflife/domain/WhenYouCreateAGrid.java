package com.wakaleo.gameoflife.domain;

import org.junit.Test;

import com.wakaleo.gameoflife.domain.Grid;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static com.wakaleo.gameoflife.domain.Cell.*;

public class WhenYouCreateAGrid {

    private static final String NEW_LINE = System.getProperty("line.separator");

    public static final String EMPTY_GRID = "..." + NEW_LINE + "..." + NEW_LINE + "..." + NEW_LINE;

    @Test
    public void aNewGridShouldBeEmpty() {
        Grid grid = new Grid();
        assertThat(grid.toString(), is(EMPTY_GRID));
    }

    @Test
    public void shouldBeAbleToSeedAGridWithAString() {

        String gridContents = "..." + NEW_LINE + "..." + NEW_LINE + "...";

        String expectedPrintedGrid = "..." + NEW_LINE + "..." + NEW_LINE + "..." + NEW_LINE;

        Grid grid = new Grid(gridContents);
        assertThat(grid.toString(), is(expectedPrintedGrid));
    }

    @Test
    public void shouldBeAbleToSeedAGridWithANonEmptyString() {

        String gridContents = "*.." + NEW_LINE + ".*." + NEW_LINE + ".*.";

        String expectedPrintedGrid = "*.." + NEW_LINE + ".*." + NEW_LINE + ".*." + NEW_LINE;

        Grid grid = new Grid(gridContents);
        assertThat(grid.toString(), is(expectedPrintedGrid));
    }

    @Test
    public void shouldBeAbleToCountLiveNeighboursOfACell() {

        String gridContents = ".*." + NEW_LINE + "..." + NEW_LINE + "...";

        Grid grid = new Grid(gridContents);
        assertThat(grid.getLiveNeighboursAt(1, 1), is(1));
    }

    @Test
    public void shouldBeAbleToCountLiveNeighboursOfACellOnBoundaries() {

        String gridContents = ".*." + NEW_LINE + "*.." + NEW_LINE + "...";

        Grid grid = new Grid(gridContents);
        assertThat(grid.getLiveNeighboursAt(0, 0), is(2));
    }

    @Test
    public void shouldBeAbleToCountLiveNeighboursOfACellInTheMiddleOfTheGrid() {

        String gridContents = "..." + NEW_LINE + "***" + NEW_LINE + "...";

        Grid grid = new Grid(gridContents);
        assertThat(grid.getLiveNeighboursAt(1, 1), is(2));
    }

    @Test
    public void shouldBeAbleToCountLiveNeighboursOfACellOnAnotherLine() {

        String gridContents = "..." + NEW_LINE + "***" + NEW_LINE + "...";

        Grid grid = new Grid(gridContents);
        assertThat(grid.getLiveNeighboursAt(1, 0), is(3));
    }

    @Test
    public void shouldBeAbleToCountLiveNeighboursOfACellOnDiagonalsAndStraightLines() {

        String gridContents = "***" + NEW_LINE + "*.*" + NEW_LINE + "***";

        Grid grid = new Grid(gridContents);
        assertThat(grid.getLiveNeighboursAt(1, 1), is(8));
    }

    @Test
    public void shouldNotCountTheTargetCellAsANeighbour() {

        String gridContents = "***" + NEW_LINE +
                "***" + NEW_LINE +
                "***";

        Grid grid = new Grid(gridContents);

        assertThat(grid.getLiveNeighboursAt(1, 1), is(8));
    }

    @Test
    public void shouldBeAbleToReadTheStateOfALivingCell() {

        String currentContents = "..." + NEW_LINE + "***" + NEW_LINE + "...\n";
        Grid grid = new Grid(currentContents);
        int x = 0;
        int y = 1;
        assertThat(grid.getCellAt(x, y), is(LIVE_CELL));
    }

    @Test
    public void shouldBeAbleToReadTheStateOfADeadCell() {

        String currentContents = "..." + NEW_LINE + "***" + NEW_LINE + "...\n";
        Grid grid = new Grid(currentContents);
        int x = 1;
        int y = 0;
        assertThat(grid.getCellAt(x, y), is(DEAD_CELL));
    }

    @Test
    public void shouldBeAbleToReadTheWidthOfTheGrid() {
        String currentContents = "..." + NEW_LINE + "***\n";
        Grid grid = new Grid(currentContents);
        assertThat(grid.getWidth(), is(3));
    }

    @Test
    public void shouldBeAbleToReadTheHeightOfTheGrid() {
        String currentContents = "..." + NEW_LINE + "***" + NEW_LINE;
        Grid grid = new Grid(currentContents);
        assertThat(grid.getHeight(), is(2));
    }

    @Test
    public void shouldBeAbleToObtainTheGridContentsAsAnArray() {
        String currentContents = "*.." + NEW_LINE + "*.." + NEW_LINE + ".*." + NEW_LINE;
        Grid grid = new Grid(currentContents);

        Cell[][] contents = grid.getContents();
        assertThat(contents[0][0], is(LIVE_CELL));
        assertThat(contents[1][0], is(LIVE_CELL));
        assertThat(contents[2][1], is(LIVE_CELL));
    }

    @Test
    public void theGridContentsAsAnArrayShouldBeTheCorrectSize() {
        String currentContents = "*.." + NEW_LINE + "*.." + NEW_LINE + ".*." + NEW_LINE;
        Grid grid = new Grid(currentContents);

        Cell[][] contents = grid.getContents();
        assertThat(contents.length, is(3));
        assertThat(contents[0].length, is(3));
    }

    @Test
    public void ModifyingTheGridContentsAsAnArrayShouldNotModifyTheOriginalContents() {
        String currentContents = "*.." + NEW_LINE + ".*." + NEW_LINE + "..*" + NEW_LINE;
        Grid grid = new Grid(currentContents);

        Cell[][] contents = grid.getContents();
        contents[1][1] = DEAD_CELL;

        assertThat(grid.getCellAt(1, 1), is(LIVE_CELL));
    }

}
