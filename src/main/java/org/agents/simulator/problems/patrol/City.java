package org.agents.simulator.problems.patrol;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class City {

    private final Map<String, Area> areasMap = new HashMap<>();

    public City(Area... areas) {
        for(Area area : areas) {
            areasMap.put(area.getName(), area);
        }
    }

    public Area getArea(String name) {
        return areasMap.get(name);
    }

    public void reset() {
        for (Area area : areasMap.values()) {
            area.reset();
        }
    }

    public Set<String> getAreas() {
        return areasMap.keySet();
    }

}
