package com.wakaleo.gameoflife.domain;

import static com.wakaleo.gameoflife.domain.Cell.LIVE_CELL;
import static com.wakaleo.gameoflife.domain.Cell.DEAD_CELL;

 /** 
 * Wakaleo Consulting - John Ferguson Smart
 * Game of life, demonstration application for Jenkins: The Definitive Guide
 *
 * Universe.java
 * A universe is a succession of grids over time.
 * Each new grid is generated from the previous one using the rules of the Game Of Life.
 */
public class Universe {

    private static final String NEW_LINE = System.getProperty("line.separator");

    private Grid currentGridContent;

	// Default constructor, called on "NEW GAME" button click
    public Universe() {
        currentGridContent = new Grid();
    }
	// Create blank grid of given size, called on "Go" button click 
    public Universe(final int rows, final int columns) {
        currentGridContent = new Grid(rows, columns);
    }
	// Create grid given cell layout, called on "Next Generation" button click 
    public Universe(final String initialGridContents) {
        currentGridContent = new Grid(initialGridContents);
    }

	// Returns the input string, functions as the Universe class' print
    public static String seededWith(final String gridContents) {
        return gridContents;
    }

	// Calculates the game-of-life's next iteration, called on "Next Generation" button click
    public void spawnsANewGeneration() {
        createNextGeneration();
    }
    public void createNextGeneration() {
        StringBuffer nextGenerationContent = new StringBuffer();
        int maxRow = currentGridContent.getWidth();
        int maxColumn = currentGridContent.getHeight();

		// Iterates over entire grid
        for (int y = 0; y < maxRow; y++) { // From top to bottom
            for (int x = 0; x < maxColumn; x++) { // From left to right
                Cell currentCell = currentGridContent.getCellAt(x, y);
                int neighbourCount = currentGridContent.getLiveNeighboursAt(x, y); // getLiveNeighboursAt() defined in Grid.java
                Cell nextCell = null;

				// Based on the rules of game-of-life, calculate next state
                if (currentCell == Cell.LIVE_CELL) { // If cell is currently LIVE
                    if ((neighbourCount == 2) || (neighbourCount == 3)) {
                        nextCell = LIVE_CELL; // Stay LIVE if 2 or 3 neighbours are LIVE
                    } else {
                        nextCell = DEAD_CELL; // Else, become DEAD due to underpopulation or overcrowding
                    }
                } else { // If cell is currently DEAD
                    if (neighbourCount == 3) {
                        nextCell = LIVE_CELL; // Become LIVE if 3 neighbours are LIVE
                    } else {
                        nextCell = DEAD_CELL; // Else, stay DEAD
                    }
                }
                nextGenerationContent.append(nextCell);
            }
            nextGenerationContent.append(NEW_LINE);
        }
        nextGenerationContent.append(NEW_LINE);

		// Sets the finalized grid of the game-of-life's next step
        currentGridContent = new Grid(nextGenerationContent.toString());
    }

	// Return the status of every cell as a string
    public String getGrid() {
        return currentGridContent.toString();
    }
	// Return the cell objects in the grid as a 2D array
    public Cell[][] getCells() {
        return currentGridContent.getContents();
    }

	// Set cell at given coordinate to LIVE
    public void setLiveCellAt(final int row, final int column) {
        this.currentGridContent.setCellAt(column, row, LIVE_CELL);
    }
	// Set cell at given coordinate to DEAD
    public void setDeadCellAt(final int row, final int column) {
        this.currentGridContent.setCellAt(column, row, DEAD_CELL);
    }

	// Return the cell object at the given coordinate
    public Cell getCellAt(final int row, final int column) {
        return currentGridContent.getCellAt(column, row);
    }
}
