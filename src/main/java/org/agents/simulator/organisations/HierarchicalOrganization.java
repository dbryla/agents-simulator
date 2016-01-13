package org.agents.simulator.organisations;

import madkit.kernel.Message;
import org.agents.simulator.Groups;
import org.agents.simulator.agents.OrganizedAgent;
import org.agents.simulator.agents.publictransport.BusDelegateAgent;
import org.agents.simulator.agents.publictransport.LineDelegateAgent;
import org.agents.simulator.organisations.messages.CommonMessage;
import org.agents.simulator.problems.Problem;
import org.agents.simulator.problems.Step;
import org.agents.simulator.problems.publictransport.BusLine;
import org.agents.simulator.problems.publictransport.BusManager;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * User: Krzysztof Nawrot
 * Date: 2015-11-25.
 */
public class HierarchicalOrganization extends Organization {
    private static final OrganizationType TYPE = OrganizationType.HIERARCHY;
    private static final int ADMINS_COUNT = 3;
    private static final int WORKERS_PER_ADMIN_COUNT = 5;
    private LinkedList<OrganizedAgent> agents = new LinkedList<>();
    private Problem problem;
    private static final String MAIN_AGENT = "main";
    private static final String ADMINISTRATOR_AGENT = "admin";
    private static final String BASIC_AGENT = "worker";

    @Override
    public void communicate(OrganizedAgent agent) {
        ArrayList<Step> listOfSteps = problem.getListOfSteps();
        if (agent.getRole().equals(MAIN_AGENT)) {
            List<Message> results = agent.broadcastMessageWithRoleAndWaitForReplies(TYPE.toString(),
                                                                        Groups.NONE.toString(),
                                                                        ADMINISTRATOR_AGENT,
                                                                        new CommonMessage(CommonMessage.Type.WORK),
                                                                        MAIN_AGENT, 60000);
            agent.broadcastMessage(TYPE.toString(), Groups.NONE.toString(), ADMINISTRATOR_AGENT,
                                    new CommonMessage(CommonMessage.Type.DIE));
            Step step = listOfSteps.get(listOfSteps.size() - 1);
            StringBuilder jsonBuilder = new StringBuilder("{\"data\": [");
            for (Message result : results) {
                jsonBuilder.append(smartCast(result).getData());
                jsonBuilder.append(",");
            }
            jsonBuilder.deleteCharAt(jsonBuilder.length() - 1);
            jsonBuilder.append("]}");
            Object finalResult = step.doIt(jsonBuilder.toString());
            problem.announceResult(finalResult);
        }
        else if (agent.getRole().contains(ADMINISTRATOR_AGENT)) {
            boolean dead = false;
            LineDelegateAgent adminAgent = (LineDelegateAgent) agent;
            while (true) {
                CommonMessage message = smartCast(adminAgent.waitNextMessage());
                adminAgent.getLogger().info("Admin got message: " + message.type);
                switch (message.type) {
                    case WORK:
                        List<Message> results = adminAgent.broadcastMessageWithRoleAndWaitForReplies(TYPE.toString(),
                                                                    adminAgent.getWorkersGroup(), BASIC_AGENT,
                                                                    new CommonMessage(CommonMessage.Type.WORK),
                                                                    ADMINISTRATOR_AGENT, 10000);
                        adminAgent.broadcastMessage(TYPE.toString(), adminAgent.getWorkersGroup(), BASIC_AGENT,
                                                    new CommonMessage(CommonMessage.Type.DIE));
                        Step step = listOfSteps.get(1);
                        int lineNum = adminAgent.getLineNum();
                        StringBuilder jsonBuilder = new StringBuilder("{\"data\": [");
                        jsonBuilder.append("{\"lineNum\": " + lineNum + ", ");
                        jsonBuilder.append("\"numOfBuses\": " + BusManager.getNumberOfBusesOnLine(lineNum) + "},");
                        for (Message result : results) {
                            jsonBuilder.append(smartCast(result).getData());
                            jsonBuilder.append(",");
                        }
                        jsonBuilder.deleteCharAt(jsonBuilder.length() - 1);
                        jsonBuilder.append("]}");
                        Object result = step.doIt(jsonBuilder.toString());
                        adminAgent.sendReply(message, new CommonMessage(result));
                        break;
                    case DIE:
                        dead = true;
                        break;
                }
                if (dead) {
                    break;
                }
            }
        }
        else {
            boolean dead = false;
            BusDelegateAgent basicAgent = (BusDelegateAgent) agent;
            while (true) {
                CommonMessage message = smartCast(basicAgent.waitNextMessage(1000));
                agent.getLogger().info("Basic got message: " + message.type);
                switch (message.type) {
                    case WORK:
                        Step step = listOfSteps.get(0);
                        Object result = step.doIt(Integer.toString(basicAgent.getMyBusId()));
                        basicAgent.sendReply(message, new CommonMessage(result));
                        break;
                    case DIE:
                        dead = true;
                        break;
                }
                if (dead) {
                    break;
                }
            }
        }
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

        agents.push(new OrganizedAgent(TYPE, MAIN_AGENT));

        BusManager.createBusLine(BusLine.LINE_144);
        agents.push(new LineDelegateAgent(TYPE, "1", ADMINISTRATOR_AGENT, BusLine.LINE_144));
        for (int j = 0; j < WORKERS_PER_ADMIN_COUNT; j++) {
            agents.push(new BusDelegateAgent(TYPE, "1", BASIC_AGENT, BusManager.createBusOnLine(BusLine.LINE_144)));
        }
        BusManager.createBusLine(BusLine.LINE_173);
        agents.push(new LineDelegateAgent(TYPE, "2", ADMINISTRATOR_AGENT, BusLine.LINE_173));
        for (int j = 0; j < WORKERS_PER_ADMIN_COUNT; j++) {
            agents.push(new BusDelegateAgent(TYPE, "2", BASIC_AGENT, BusManager.createBusOnLine(BusLine.LINE_173)));
        }
        BusManager.createBusLine(BusLine.LINE_501);
        agents.push(new LineDelegateAgent(TYPE, "3", ADMINISTRATOR_AGENT, BusLine.LINE_501));
        for (int j = 0; j < WORKERS_PER_ADMIN_COUNT; j++) {
            agents.push(new BusDelegateAgent(TYPE, "3", BASIC_AGENT, BusManager.createBusOnLine(BusLine.LINE_501)));
        }

        return agents;
    }
}
