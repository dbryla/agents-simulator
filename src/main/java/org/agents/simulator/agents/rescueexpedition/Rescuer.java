package org.agents.simulator.agents.rescueexpedition;

import org.agents.simulator.agents.OrganizedAgent;
import org.agents.simulator.organisations.OrganizationType;

/**
 * User: Krzysztof Nawrot
 * Date: 2016-01-10.
 */
public class Rescuer extends OrganizedAgent {
    private final String rescueGroup;
    private final int areaOfSearches;

    public Rescuer(OrganizationType organization, String rescueGroup, String role, int areaOfSearches) {
        super(organization, rescueGroup, role);
        this.rescueGroup = rescueGroup;
        this.areaOfSearches = areaOfSearches;
    }

    public String getRescuerGroup() {
        return rescueGroup;
    }

    public int getAreaOfSearches() {
        return areaOfSearches;
    }

}
