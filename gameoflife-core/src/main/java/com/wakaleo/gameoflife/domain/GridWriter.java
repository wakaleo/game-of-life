package com.wakaleo.gameoflife.domain;

public class GridWriter {

    public String convertToString(Cell[][] gridContents) {
        StringBuffer printedGrid = new StringBuffer();
        for(Cell[] row : gridContents) {
            for( Cell cell : row) {
                printedGrid.append(cell.toString());
            }
            // TODO: This simply masks the problem: why empty rows being passed?
            if(row.length > 0) {
            	printedGrid.append("\n");
            }
        }
        return printedGrid.toString();
    }
}
