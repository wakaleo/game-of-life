package com.wakaleo.gameoflife.domain;

import static com.wakaleo.gameoflife.domain.Cell.DEAD_CELL;
import static com.wakaleo.gameoflife.domain.Cell.LIVE_CELL;

 /**
 * Wakaleo Consulting - John Ferguson Smart
 * Game of life, demonstration application for Jenkins: The Definitive Guide
 *
 * Grid.java
 * Grid object class, containing information on a collection of cells
 */
public class Grid {

    private static final int DEFAULT_ROW_COUNT = 3;
    private static final int DEFAULT_COLUMN_COUNT = 3;

    private Cell[][] cells;

	// Helper classes with functions to access cell information
    private GridReader gridReader = new GridReader();
    private GridWriter gridWriter = new GridWriter();

	// Default constructor, called on "NEW GAME" button click
    public Grid() {
        this.cells = anArrayOfDeadCells(DEFAULT_ROW_COUNT, DEFAULT_COLUMN_COUNT);
    }
	// Create blank grid of given size, called on "Go" button click 
    public Grid(final int rows, final int columns) {
        this.cells = anArrayOfDeadCells(rows, columns);
    }
	// Create grid given cell layout, called on "Next Generation" button click 
    public Grid(final String gridContents) {
        this.cells = makeCellArrayFrom(gridContents);
    }

	// Convert input string of symbols into 2D array of cell objects
    private Cell[][] makeCellArrayFrom(final String gridContents) {
        return gridReader.loadFrom(gridContents);
    }

	// Helper function, populates grid with dead cells
    private Cell[][] anArrayOfDeadCells(final int rows, final int columns) {
        Cell[][] deadCells = new Cell[rows][columns]; // First create an empty 2D array of cells
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                deadCells[i][j] = DEAD_CELL; // Then set each one's status to DEAD
            }
        }
        return deadCells;
    }

	// Override function for easy printing of entire grids
    @Override
    public String toString() {
        return gridWriter.convertToString(cells);
    }

	// Calculates the number of neighbours with LIVE status, called when creating the game-of-life's next step
    public int getLiveNeighboursAt(final int x, final int y) {
        int liveNeighbourCount = 0;
		// "neighbouring cells" are positions adjacent horizontally, vertically, and diagonally
        for (int xPosition = x - 1; xPosition <= x + 1; xPosition++) {
            for (int yPosition = y - 1; yPosition <= y + 1; yPosition++) {
                if (!cellIsCentralCell(xPosition, yPosition, x, y)) { // Cell does not count itself as a neighbour
                    liveNeighbourCount += countLiveNeighboursInCell(xPosition, yPosition); // Increment counter if LIVE
                }
            }
        }
        return liveNeighbourCount;
    }
	// Helper function, returns 1 if the cell at the given coordinate is LIVE, else 0
    private int countLiveNeighboursInCell(final int x, final int y) {
        if (cellIsOutsideBorders(x, y)) {
            return 0;
        }
        if (cells[y][x] == LIVE_CELL) {
            return 1;
        } else {
            return 0;
        }
    }
	// Helper function, validates if given coordinate is within the grid size
    private boolean cellIsOutsideBorders(final int x, final int y) {
        return (y < 0 || y > getMaxRow()) || (x < 0 || x > getMaxColumn());
    }

    private int getMaxRow() {
        return cells.length - 1;
    }

    private int getMaxColumn() {
        return cells[0].length - 1;
    }

	// Makes sure cell does not count itself as a neighbour
	// In a simple example, creating a new validation function may not be necessary
	// However, if validation later becomes complex, a seperate function is good practice
    private boolean cellIsCentralCell(final int x, final int y,
                                      final int centerX, final int centerY) {
        return (x == centerX) && (y == centerY);
    }

    public Cell getCellAt(final int x, final int y) {
        return cells[y][x];
    }

    public int getWidth() {
        return cells[0].length;
    }

    public int getHeight() {
        return cells.length;
    }

    public void setCellAt(final int x, final int y, final Cell cell) {
        cells[y][x] = cell;
    }

	// Returns entire grid as 2D array of cell objects
    public Cell[][] getContents() {
        Cell[][] contentCopy = new Cell[getHeight()][getWidth()];
        for (int row = 0; row < getHeight(); row++) {
            for (int column = 0; column < getWidth(); column++) {
                contentCopy[row][column] = cells[row][column];
            }
        }
        return contentCopy;
    }
}
