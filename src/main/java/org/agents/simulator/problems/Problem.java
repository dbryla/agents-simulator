package org.agents.simulator.problems;

import java.util.ArrayList;

public interface Problem {
    ArrayList<Step> getListOfSteps();

    void announceResult(Object finalResult);
}

