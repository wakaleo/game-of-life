package com.wakaleo.gameoflife.domain;

 /**
 * Wakaleo Consulting - John Ferguson Smart
 * Game of life, demonstration application for Jenkins: The Definitive Guide
 *
 * Cell.java
 * Cell object class, contains information on individual cells (positions) in the grid
 */
public enum Cell {
	// Symbols to represent cell status
    LIVE_CELL("7"), DEAD_CELL(".");

    private String symbol;

    private Cell(final String initialSymbol) {
        this.symbol = initialSymbol;
    }

	// Override function for easy printing of cell's symbol
	// Functions identically to default getter ( getSymbol() )
    @Override
    public String toString() {
        return symbol;
    }

	// Function used for creating a cell given a string
    static Cell fromSymbol(final String symbol) {
        Cell cellRepresentedBySymbol = null;
        for (Cell cell : Cell.values()) {
            if (cell.symbol.equals(symbol)) {
                cellRepresentedBySymbol = cell;
                break;
            }
        }
        return cellRepresentedBySymbol;
    }

	// Default getter
    public String getSymbol() {
        return symbol;
    }
}
