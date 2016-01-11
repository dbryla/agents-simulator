package org.agents.simulator.agents.rescueexpedition;

import org.agents.simulator.Groups;
import org.agents.simulator.agents.OrganizedAgent;
import org.agents.simulator.organisations.OrganizationType;

/**
 * User: Krzysztof Nawrot
 * Date: 2016-01-10.
 */
public class RescueTeamCommander extends OrganizedAgent {
    private final String rescueGroup;
    private int lostAlreadyFound = 0;
    private int foundInMyTeam = 0;

    public RescueTeamCommander(OrganizationType organization, String role, String rescueGroup) {
        super(organization, Groups.NONE.toString(), role);
        this.rescueGroup = rescueGroup;
    }

    @Override
    public void activate() {
        super.activate();
        if (!createGroupIfAbsent(organization.toString(), rescueGroup)) {
            requestRole(organization.toString(), rescueGroup, role);
        }
    }

    public int getLostAlreadyFound() {
        return lostAlreadyFound;
    }

    public void incrementLostAlreadyFound() {
        lostAlreadyFound++;
    }

    public String getRescueGroup() {
        return rescueGroup;
    }

    public int getFoundInMyTeam() {
        return foundInMyTeam;
    }

    public void incrementFoundInMyTeam() {
        foundInMyTeam++;
    }



}
