package com.wakaleo.gameoflife.webtests.controllers;

import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.wakaleo.gameoflife.domain.Universe;

@Controller
@RequestMapping("/game")
public class GameController {

	Random randomGenerator = new Random();

	@RequestMapping("/new")
	public ModelAndView newGame() {
		ModelAndView mav = new ModelAndView("game/edit");
		Universe universe = new Universe();
		mav.addObject("universe", universe);
		thinkABit(250);
		return mav;
	}

	@RequestMapping("/start")
	public ModelAndView firstGeneration(@RequestParam("rows") int rows,
			@RequestParam("columns") int columns, HttpServletRequest request) {

		Universe universe = universeInstanciatedFromClickedCells(rows, columns, request);
		// TODO: Hate this code
		thinkABit(200);

		return showGridScreen(universe);
	}

	@RequestMapping("/next")
	public ModelAndView nextGeneration(@RequestParam("rows") int rows,
			@RequestParam("columns") int columns, HttpServletRequest request) {

		Universe universe = universeInstanciatedFromClickedCells(rows, columns,
				request);
		universe.createNextGeneration();

		thinkABit(250);

		return showGridScreen(universe);
	}

	private void thinkABit(int max) {
		int thinkingTime = randomGenerator.nextInt(max/4);
		try {
			Thread.currentThread().sleep(thinkingTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @deprecated
	 */
	private Universe universeInstanciatedByDimensions(int rows,int columns) {
		Universe universe = new Universe(rows, columns);
		for (int row = 0; row < rows; row++) {
			for (int column = 0; column < columns; column++) {
					universe.setDeadCellAt(row, column);
			}
		}
		return universe;
	}

	private Universe universeInstanciatedFromClickedCells(int rows,
			int columns, HttpServletRequest request) {
		Universe universe = universeInstanciatedByDimensions(rows, columns);
		// TODO: tidy up
		for (int row = 0; row < rows; row++) {
			for (int column = 0; column < columns; column++) {
				if (cellWasClickedAt(row, column, request)) {
					universe.setLiveCellAt(row, column);
				}
			}
		}
		return universe;
	}

	private ModelAndView showGridScreen(Universe universe) {
		ModelAndView mav = new ModelAndView("game/show");
		mav.addObject("universe", universe);
		mav.addObject("rows", universe.getCells().length);
		mav.addObject("columns", universe.getCells()[0].length);
		return mav;
	}

	private boolean cellWasClickedAt(int row, int column,
			HttpServletRequest request) {
		String cellName = "cell_" + row + "_" + column;
		return (request.getParameter(cellName) != null);
	}
}