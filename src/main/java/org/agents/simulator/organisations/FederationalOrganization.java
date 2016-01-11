package org.agents.simulator.organisations;

import madkit.kernel.Message;
import org.agents.simulator.Groups;
import org.agents.simulator.agents.OrganizedAgent;
import org.agents.simulator.agents.rescueexpedition.RescueTeamCommander;
import org.agents.simulator.agents.rescueexpedition.Rescuer;
import org.agents.simulator.organisations.messages.CommonMessage;
import org.agents.simulator.problems.Problem;
import org.agents.simulator.problems.Step;
import org.agents.simulator.problems.rescueexpedition.AreaOfSearches;
import org.agents.simulator.problems.rescueexpedition.RescueExpedition;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * User: Krzysztof Nawrot
 * Date: 2016-01-10.
 */
public class FederationalOrganization extends Organization {
    private static final OrganizationType TYPE = OrganizationType.FEDERATION;
    public static final String COMMANDER = "commander";
    public static final String RESCUER = "rescuer";

    private LinkedList<OrganizedAgent> agents = new LinkedList<>();
    private Problem problem;

    @Override
    public void communicate(OrganizedAgent agent) {
        ArrayList<Step> listOfSteps = problem.getListOfSteps();
        if (agent.getRole().equals(COMMANDER)) {
            RescueTeamCommander commanderAgent = (RescueTeamCommander) agent;
            commanderAgent.broadcastMessageWithRole(TYPE.toString(),
                    commanderAgent.getRescueGroup(),
                    RESCUER,
                    new CommonMessage(CommonMessage.Type.WORK),
                    COMMANDER);
            boolean dead = false;
            while (true) {
                CommonMessage message = smartCast(commanderAgent.waitNextMessage());
                switch (message.type) {
                    case WORK:
                        commanderAgent.incrementLostAlreadyFound();
                        break;
                    case RESULT:
                        int foundLost = Integer.parseInt((String) message.getData());
                        for (int i = 0; i < foundLost; i++) {
                            commanderAgent.incrementFoundInMyTeam();
                            commanderAgent.incrementLostAlreadyFound();
                            commanderAgent.broadcastMessageWithRole(TYPE.toString(),
                                    Groups.NONE.toString(),
                                    COMMANDER,
                                    new CommonMessage(CommonMessage.Type.WORK),
                                    COMMANDER);
                        }
                        break;
                    case DIE:
                        printResults(listOfSteps, commanderAgent);
                        dead = true;
                        break;

                }
                if (dead) {
                    break;
                }
                if (commanderAgent.getLostAlreadyFound() == RescueExpedition.LOST) {
                    commanderAgent.broadcastMessageWithRole(TYPE.toString(),
                            Groups.NONE.toString(),
                            COMMANDER,
                            new CommonMessage(CommonMessage.Type.DIE),
                            COMMANDER);
                    printResults(listOfSteps, commanderAgent);
                    break;
                }
            }
        } else {
            Rescuer rescuer = (Rescuer) agent;
            Message message = rescuer.waitNextMessage();
            Step step = listOfSteps.get(0);
            Object result = step.doIt(Integer.toString(rescuer.getAreaOfSearches()));
            rescuer.sendReply(message, new CommonMessage(result));
        }
    }

    private void printResults(ArrayList<Step> listOfSteps, RescueTeamCommander commanderAgent) {
        Step step = listOfSteps.get(listOfSteps.size() - 1);
        Object finalResult = step.doIt(commanderAgent.getRescueGroup() + ":"
                + Integer.toString(commanderAgent.getFoundInMyTeam()));
        problem.announceResult(finalResult);
    }


    private CommonMessage smartCast(Message m) {
        if (m instanceof CommonMessage) {
            return (CommonMessage) m;
        }
        throw new RuntimeException("Unexpected behaviour!");
    }

    @Override
    public LinkedList<OrganizedAgent> createAndAssignProblem(Problem problem) {
        this.problem = problem;

        AreaOfSearches.setUp();
        agents.push(new RescueTeamCommander(TYPE, COMMANDER, "Alpha"));
        for (int j = 0; j < RescueExpedition.RESCUERS_PER_TEAM_COUNT; j++) {
            agents.push(new Rescuer(TYPE, "Alpha", RESCUER, AreaOfSearches.nextAreaOfSearches()));
        }
        agents.push(new RescueTeamCommander(TYPE, COMMANDER, "Bravo"));
        for (int j = 0; j < RescueExpedition.RESCUERS_PER_TEAM_COUNT; j++) {
            agents.push(new Rescuer(TYPE, "Bravo", RESCUER, AreaOfSearches.nextAreaOfSearches()));
        }
        agents.push(new RescueTeamCommander(TYPE, COMMANDER, "Gamma"));
        for (int j = 0; j < RescueExpedition.RESCUERS_PER_TEAM_COUNT; j++) {
            agents.push(new Rescuer(TYPE, "Gamma", RESCUER, AreaOfSearches.nextAreaOfSearches()));
        }

        return agents;
    }
}
