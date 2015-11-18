package org.agents.simulator.organisations;

import madkit.kernel.Agent;
import org.agents.simulator.agents.OrganizedAgent;
import org.agents.simulator.problems.Problem;

import java.util.HashMap;
import java.util.LinkedList;

public abstract class Organization {

    private static final HashMap<OrganizationType, Organization> organizations = new HashMap<>();

    static {
        organizations.put(OrganizationType.SLAVERY, new SlaveryOrganization());
    }

    public static Organization getOrganizationPerType(OrganizationType type) {
        return organizations.get(type);
    }

    abstract public void communicate(OrganizedAgent agent);

    abstract public LinkedList<OrganizedAgent> createAndAssignProblem(Problem problem);
}
