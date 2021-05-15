package com.wakaleo.gameoflife.webtests.controllers;

import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.wakaleo.gameoflife.domain.Universe;

 /**
 * Wakaleo Consulting - John Ferguson Smart
 * Game of life, demonstration application for Jenkins: The Definitive Guide
 *
 * GameController.java
 * Controller to navigate the buttons on the game pages
 */
@Controller
@RequestMapping("/game")
public class GameController {
	// For generating random thread sleep times
    private Random randomGenerator = new Random();
	
	//for checking pollscm

	// Clicking the "New Game" button on the main page loads the cell seletion page
    @RequestMapping("/new")
    public ModelAndView newGame() {
        ModelAndView mav = new ModelAndView("game/edit");
        Universe universe = new Universe();
        mav.addObject("universe", universe);
        thinkABit(250); // Pause for random time
        return mav;
    }

	// Clicking the "Go" button on the cell selection page loads the initial step of the game
    @RequestMapping("/start")
    public ModelAndView firstGeneration(@RequestParam("rows") final int rows,
                                        @RequestParam("columns") final int columns,
                                        final HttpServletRequest request) {

        Universe universe = universeInstanciatedFromClickedCells(rows, columns, request);
        thinkABit(200); // Pause for random time

        return showGridScreen(universe);
    }

	// Clicking the "Next Generation" button loads the next step of the game
    @RequestMapping("/next")
    public ModelAndView nextGeneration(@RequestParam("rows") final int rows,
                                       @RequestParam("columns") final int columns,
                                       final HttpServletRequest request) {

        Universe universe = universeInstanciatedFromClickedCells(rows, columns, request);
        universe.createNextGeneration();

        thinkABit(250); // Pause for random time

        return showGridScreen(universe);
    }

	// Pause for a random time between 0 and given input divided by 4
    private void thinkABit(final int max) {
        int thinkingTime = getRandomGenerator().nextInt(max / 4);
        try {
            Thread.currentThread().sleep(thinkingTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

	// Creates new grid for the next step, initialize with all dead cells
    private Universe universeInstanciatedByDimensions(final int rows, final int columns) {
        Universe universe = new Universe(rows, columns);
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                universe.setDeadCellAt(row, column); // Dead state at every cell position
            }
        }
        return universe;
    }
	// Populate the created grid with live cells where selected
    private Universe universeInstanciatedFromClickedCells(final int rows,
                                                          final int columns,
                                                          final HttpServletRequest request) {
        Universe universe = universeInstanciatedByDimensions(rows, columns);
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                if (cellWasClickedAt(row, column, request)) { // Find if checkbox was selected on the previous page
                    universe.setLiveCellAt(row, column);
                }
            }
        }
        return universe;
    }

	// Display the game-of-life grid page
    private ModelAndView showGridScreen(final Universe universe) {
        ModelAndView mav = new ModelAndView("game/show");
        mav.addObject("universe", universe);
        mav.addObject("rows", universe.getCells().length);
        mav.addObject("columns", universe.getCells()[0].length);
        return mav;
    }

	// Determines if the user selected the checkbox at the given coordinate
    private boolean cellWasClickedAt(final int row,
                                     final int column,
                                     final HttpServletRequest request) {
        String cellName = "cell_" + row + "_" + column;
        return (request.getParameter(cellName) != null);
    }

	// Getter for this class' random number generator
	// Good practice to avoid directly accessing attributes, use getters and setters instead
    private Random getRandomGenerator() {
        return randomGenerator;
    }
}
