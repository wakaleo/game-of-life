package com.wakaleo.gameoflife.webtests.requirements;

import net.thucydides.core.annotations.Feature;

public class GameOfLifeApplication {

    @Feature
    public class RunSimulations {
        public class RunASimulation {}
    }

    @Feature
    public class LearnApplication {
        public class ViewHomePage {}
    }

    @Feature
    public class History {
        public class ViewHistory {}
    }

    @Feature
    public class RecordResults {
        public class RecordASimulation {}
        public class ViewPastSimulationResults {}
        public class ViewSimulationStatistics {}
    }
}
