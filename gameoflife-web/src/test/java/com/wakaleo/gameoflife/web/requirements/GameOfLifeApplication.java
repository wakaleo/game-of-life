package com.wakaleo.gameoflife.web.requirements;

import net.thucydides.core.annotations.Feature;

public class GameOfLifeApplication {

    @Feature
    public class RunSimulations {
        public class RunASimulation {}
    }

    @Feature
    public class RecordResults {
        public class RecordASimulation {}
        public class ViewPastSimulationResults {}
        public class ViewSimulationStatistics {}
    }
}
