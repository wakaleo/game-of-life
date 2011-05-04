package com.wakaleo.gameoflife.domain

import static com.wakaleo.gameoflife.domain.Universe.seededWith;

description "This story describes how new generations are spawned in the GOL universe"

narrative "story description", {
	as_a "game of life player"
	i_want "to be able to spawn a new generation of cells from an existing one"
	so_that "that I can study how the rules behave"
}

scenario "A universe seeded with an empty grid will spawn an empty grid",{
	given "an empty initial grid"
	and "a universe seeded with this grid"
	when "the next generation is spawned"
	then "the universe should contain an empty grid"
}

scenario "A universe seeded with a single living cell will spawn an empty grid",{
	given "an initial grid"
	and "a universe seeded with this grid"
	when "the next generation is spawned"	
	then "the universe should contain an empty grid"
}