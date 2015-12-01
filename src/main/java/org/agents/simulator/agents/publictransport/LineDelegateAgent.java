package org.agents.simulator.agents.publictransport;

import org.agents.simulator.Groups;
import org.agents.simulator.agents.OrganizedAgent;
import org.agents.simulator.organisations.OrganizationType;

/**
 * User: Krzysztof Nawrot
 * Date: 2015-11-25.
 */
public class LineDelegateAgent extends OrganizedAgent {
    private final int lineNum;
    private final String workersGroup;
    private final String role;

    public LineDelegateAgent(OrganizationType organization, String workersGroup, String role, int lineNum) {
        super(organization, Groups.NONE.toString(), role);
        this.lineNum = lineNum;
        this.workersGroup = workersGroup;
        this.role = role;
    }

    public String getWorkersGroup() {
        return workersGroup;
    }

    @Override
    public void activate() {
        super.activate();
        if (!createGroupIfAbsent(organization.toString(), workersGroup)) {
            requestRole(organization.toString(), workersGroup, role);
        }
    }

    public int getLineNum() {
        return lineNum;
    }
}
