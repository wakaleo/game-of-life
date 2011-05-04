package com.wakaleo.gameoflife.domain;

public class GridWriter {

    public String convertToString(Cell[][] gridContents) {
        StringBuffer printedGrid = new StringBuffer();
        for(Cell[] row : gridContents) {
            for( Cell cell : row) {
                printedGrid.append(cell.toString());
            }
            printedGrid.append("\n");
        }
        return printedGrid.toString();
    }
}
