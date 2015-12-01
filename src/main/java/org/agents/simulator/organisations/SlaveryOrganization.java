package org.agents.simulator.organisations;

import madkit.kernel.Message;
import org.agents.simulator.Groups;
import org.agents.simulator.agents.OrganizedAgent;
import org.agents.simulator.organisations.messages.CommonMessage;
import org.agents.simulator.problems.Problem;
import org.agents.simulator.problems.Step;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class SlaveryOrganization extends Organization {

    private static final OrganizationType TYPE = OrganizationType.SLAVERY;
    private LinkedList<OrganizedAgent> agents = new LinkedList<>();
    private Problem problem;
    private static final String MASTER = "master";
    private static final String SLAVE = "slave";

    @Override
    public void communicate(OrganizedAgent agent) {
        ArrayList<Step> listOfSteps = problem.getListOfSteps();
        if (agent.getRole().equals(MASTER)) {
            List<Message> results = agent.broadcastMessageWithRoleAndWaitForReplies(TYPE.toString(), Groups.NONE.toString(),
                    SLAVE, new CommonMessage(CommonMessage.Type.WORK), MASTER, 3000);
            agent.broadcastMessage(TYPE.toString(), Groups.NONE.toString(), SLAVE, new CommonMessage(CommonMessage.Type.DIE));
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
        } else {
            boolean dead = false;
            while(true) {
                CommonMessage message = smartCast(agent.waitNextMessage());
                agent.getLogger().info("Slave got message: " + message.type);
                switch (message.type) {
                    case WORK:
                        Step step = listOfSteps.get(0);
                        Object result = step.doIt(null);
                        agent.sendReply(message, new CommonMessage(result));
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
        agents.push(new OrganizedAgent(TYPE, MASTER));
        int teamSize = 10;
        for (int i = 0; i < teamSize; ++i) {
            agents.push(new OrganizedAgent(TYPE, SLAVE));
        }
        return agents;
    }
}
