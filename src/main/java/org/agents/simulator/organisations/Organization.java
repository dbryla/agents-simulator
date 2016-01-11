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
        organizations.put(OrganizationType.HIERARCHY, new HierarchicalOrganization());
        organizations.put(OrganizationType.COALITION, new CoalitionOrganization());
        organizations.put(OrganizationType.FEDERATION, new FederationalOrganization());
    }

    public static Organization getOrganizationPerType(OrganizationType type) {
        if (!organizations.containsKey(type)) {
            throw new OrganizationNotFoundException(type);
        }
        return organizations.get(type);
    }

    abstract public void communicate(OrganizedAgent agent);

    abstract public LinkedList<OrganizedAgent> createAndAssignProblem(Problem problem);

    private static class OrganizationNotFoundException extends RuntimeException {
        private final OrganizationType organizationType;

        public OrganizationNotFoundException(OrganizationType type) {
            organizationType = type;
        }

        @Override
        public String getMessage() {
            return "Organization " + organizationType + " not found!";
        }
    }
}
