package org.agents.simulator.problems.patrol;

import java.util.*;

public class Area {
    private Queue<String> remainingStreets;
    private String name;
    private String[] streets;

    public Area(String name, String... streets) {
        this.name = name;
        this.streets = streets;
    }

    public String getName() {
        return name;
    }

    public String getRemainingStreet() {
        return remainingStreets.poll();
    }

    public void reset() {
        remainingStreets = new LinkedList<>(Arrays.asList(streets));
    }
}
