package org.agents.simulator.agents.publictransport;

import org.agents.simulator.Groups;
import org.agents.simulator.agents.OrganizedAgent;
import org.agents.simulator.organisations.OrganizationType;

/**
 * User: Krzysztof Nawrot
 * Date: 2015-11-25.
 */
public class BusDelegateAgent extends OrganizedAgent {
    private final int myBusId;
    private final String group;
    private final String role;

    public BusDelegateAgent(OrganizationType organization, String group, String role, int myBusId) {
        super(organization, Groups.NONE.toString(), role);
        this.myBusId = myBusId;
        this.group = group;
        this.role = role;
    }

    @Override
    public void activate() {
        super.activate();
        if (!createGroupIfAbsent(organization.toString(), group)) {
            requestRole(organization.toString(), group, role);
        }
    }

    public int getMyBusId() {
        return myBusId;
    }
}
