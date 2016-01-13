package org.agents.simulator.organisations;

import org.agents.simulator.PseudoNumberGenerator;
import org.agents.simulator.agents.OrganizedAgent;
import org.agents.simulator.problems.Problem;
import org.agents.simulator.problems.Step;
import org.agents.simulator.problems.patrol.CitiesDatabase;
import org.agents.simulator.problems.patrol.City;
import org.agents.simulator.problems.patrol.PatrolledArea;

import java.util.Iterator;
import java.util.LinkedList;

public class CoalitionOrganization extends Organization {

    private static final String MEMBER = "member";
    private Problem problem;
    private static final OrganizationType TYPE = OrganizationType.COALITION;
    private LinkedList<OrganizedAgent> agents = new LinkedList<>();

    @Override
    public void communicate(OrganizedAgent agent) {
        Iterator<Step> stepIterator = problem.getListOfSteps().iterator();
        String result = stepIterator.next().doIt(agent.getGroup());
        if (result != null) {
            String finalResult = stepIterator.next().doIt(result);
            problem.announceResult(
                    String.format("Report for district %s: Street %s is %spatrolled.",
                            agent.getGroup(),
                            result,
                            finalResult.equals("true") ? "" : "not "));
        }

    }

    @Override
    public LinkedList<OrganizedAgent> createAndAssignProblem(Problem problem) {
        this.problem = problem;
        int teamSize = PseudoNumberGenerator.nextInt(10);
        if (problem instanceof PatrolledArea) {
            String cityName = ((PatrolledArea) problem).getCityName();
            City city = CitiesDatabase.getCity(cityName);
            System.out.println("Number of agents " + teamSize * city.getAreas().size());
            System.out.println("Checking police patrols in " + cityName);
            for (String areaName : city.getAreas()) {
                for (int i = 0; i < teamSize; ++i) {
                    agents.push(new OrganizedAgent(TYPE, areaName, MEMBER));
                }
            }
        }
        return agents;
    }
}
