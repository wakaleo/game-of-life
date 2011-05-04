package com.wakaleo.gameoflife.domain

import static com.wakaleo.gameoflife.domain.Universe.seededWith;

description "This story describes how new generations are spawned in the GOL universe"

narrative "story description", {
	as_a "game of life player"
	i_want "to be able to spawn a new generation of cells from an existing one"
	so_that "that I can study how the rules behave"
}


examples "Grid examples with '#{initialGrid}' and #{expectedGrid}", {
  initialGrid  = ["...\n...\n...\n",   "...\n.*.\n...\n",   "...\n**.\n...\n"]
  expectedGrid = ["...\n...\n...\n",   "...\n...\n...\n",   "...\n...\n...\n"]
}

scenario "A universe seeded with an initial grid of #initialGrid will spawn a grid like #expectedGrid",{
	given "a universe seeded with #initialGrid", {
		theUniverse = new Universe(seededWith(initialGrid))
	}
	when "the next generation is spawned", {
		theUniverse.spawnsANewGeneration()
	}
	
	then "the universe should be #expectedGrid", {
		theUniverse.grid.shouldBe expectedGrid
	}
}