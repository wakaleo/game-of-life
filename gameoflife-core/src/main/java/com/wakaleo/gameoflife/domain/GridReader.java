package com.wakaleo.gameoflife.domain;

import java.util.ArrayList;
import java.util.List;

public class GridReader {

    private static final String NEW_LINE = System.getProperty("line.separator");

    public Cell[][] loadFrom(final String gridContents) {
        List<Cell[]> rows = new ArrayList<Cell[]>();
        String[] rowValues = splitIntoRows(gridContents);
        for (String row : rowValues) {
            Cell[] cellsInRow = splitIntoCells(row);
            rows.add(cellsInRow);
        }
        return (Cell[][]) rows.toArray(new Cell[0][0]);
    }

    private Cell[] splitIntoCells(final String row) {
        // TODO: ugly code
        char[] cellSymbols = row.trim().toCharArray();
        List<Cell> cellsInRow = new ArrayList<Cell>();
        for (char cellSymbol : cellSymbols) {
            Cell cell = Cell.fromSymbol(Character.toString(cellSymbol));
            if (cell == null) {
                throw new IllegalArgumentException();
            }
            cellsInRow.add(cell);
        }
        return cellsInRow.toArray(new Cell[0]);
    }

    private String[] splitIntoRows(final String gridContents) {
        return gridContents.split(NEW_LINE);
    }

}
