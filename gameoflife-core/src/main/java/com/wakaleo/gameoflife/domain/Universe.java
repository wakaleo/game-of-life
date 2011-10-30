package com.wakaleo.gameoflife.domain;

import static com.wakaleo.gameoflife.domain.Cell.*;

/**
 * A universe is a succession of grids over time.
 * Each new grid is generated from the previous one using the rules of the Game Of Life.
 *
 */
public class Universe {

    private static final String NEW_LINE = System.getProperty("line.separator");

    private Grid currentGridContent;

    public Universe() {
        currentGridContent = new Grid();
    }

    public Universe(int rows, int columns) {
        currentGridContent = new Grid(rows, columns);
    }

    public Universe(String initialGridContents) {
	    currentGridContent = new Grid(initialGridContents);
	}

	public static String seededWith(String gridContents) {
		return gridContents;
	}

	public void spawnsANewGeneration() {
		createNextGeneration();
	}

    public void createNextGeneration() {

        StringBuffer nextGenerationContent = new StringBuffer();

        int maxRow = currentGridContent.getWidth();
        int maxColumn = currentGridContent.getHeight();
        // TODO: simplify this code
        for (int y = 0; y < maxRow; y++) {
            for (int x = 0; x < maxColumn; x++) {
                Cell currentCell = currentGridContent.getCellAt(x, y);
                int neighbourCount = currentGridContent.getLiveNeighboursAt(x,y);
                Cell nextCell = null;
                if (currentCell == Cell.LIVE_CELL) {
                    if ((neighbourCount == 2) || (neighbourCount == 3)) {
                        nextCell = LIVE_CELL;
                    } else {
                        nextCell = DEAD_CELL;
                    }
                } else {
                    if (neighbourCount == 3) {
                        nextCell = LIVE_CELL;
                    } else {
                        nextCell = DEAD_CELL;
                    }
                }
                nextGenerationContent.append(nextCell);
            }
            nextGenerationContent.append(NEW_LINE);
        }
        nextGenerationContent.append(NEW_LINE);
        currentGridContent = new Grid(nextGenerationContent.toString());
    }

    public String getGrid() {
        return currentGridContent.toString();
    }

    public Cell[][] getCells() {
        return currentGridContent.getContents();
    }

    public void setLiveCellAt(int row, int column) {
        this.currentGridContent.setCellAt(column, row, LIVE_CELL);
    }

    public Cell getCellAt(int row, int column) {
        return currentGridContent.getCellAt(column,row);
    }

	public void setDeadCellAt(int row, int column) {
        this.currentGridContent.setCellAt(column, row, DEAD_CELL);
	}
}
