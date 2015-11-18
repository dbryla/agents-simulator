package org.agents.simulator;

import org.agents.simulator.agents.OrganizedAgent;
import org.agents.simulator.organisations.Organization;
import org.agents.simulator.organisations.OrganizationType;
import org.agents.simulator.problems.Problem;

import java.util.LinkedList;

public class Simulator {
    private final OrganizationType organization;
    private final Problem problem;
    private LinkedList<OrganizedAgent> agents;

    public Simulator(OrganizationType organization, Problem problem) {
        this.organization = organization;
        this.problem = problem;
    }

    public void createOrganization() {
        agents = Organization.getOrganizationPerType(organization).createAndAssignProblem(problem);
    }

    public void start() {
        System.out.println("Starting agents from organization!");
        for (OrganizedAgent agent : agents) {
            AgentsManager.startAgent(agent);
        }
    }
}
