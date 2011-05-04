package com.wakaleo.gameoflife.domain

import static com.wakaleo.gameoflife.domain.Universe.seededWith;

description "This story describes how new generations are spawned in the GOL universe"

narrative "story description", {
	as_a "game of life player"
	i_want "to be able to spawn a new generation of cells from an existing one"
	so_that "that I can study how the rules behave"
}

scenario "A universe seeded with an empty grid will spawn an empty grid",{
	given "an empty initial grid", {
		anEmptyGrid = "...\n...\n...\n"		
	}
	and "a universe seeded with this grid", {
		theUniverse = new Universe(seededWith(anEmptyGrid))
	}
	when "the next generation is spawned", {
		theUniverse.spawnsANewGeneration()
	}
	
	then "the universe should contain an empty grid", {
		theUniverse.grid.shouldBe anEmptyGrid
	}
}

scenario "A universe seeded with a single living cell will spawn an empty grid",{
	given "an initial grid", {
		anInitialGridWithOneCell = "...\n.*.\n...\n"		
	}
	and "a universe seeded with this grid", {
		theUniverse = new Universe(seededWith(anInitialGridWithOneCell))
	}
	when "the next generation is spawned", {
		theUniverse.spawnsANewGeneration()
	}
	
	then "the universe should contain an empty grid", {
		anEmptyGrid = "...\n...\n...\n"		
		theUniverse.grid.shouldBe anEmptyGrid
	}
}