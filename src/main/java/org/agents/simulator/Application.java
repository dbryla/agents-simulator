package org.agents.simulator;

import madkit.kernel.AbstractAgent;
import madkit.kernel.Madkit;
import org.agents.simulator.organisations.OrganizationType;
import org.agents.simulator.problems.Problem;
import org.agents.simulator.problems.patrol.PatrolledArea;
import org.agents.simulator.problems.pi.ValueOfPI;
import org.agents.simulator.problems.publictransport.PublicTransport;
import org.agents.simulator.problems.rescueexpedition.RescueExpedition;

public class Application extends AbstractAgent {
    public static void main(String[] args) throws InterruptedException {
        // Run MaDKit with an empty agent to initialize kernel
        AgentsManager.create(new Madkit(new String[] {"--launchAgents org.agents.simulator.Application"}));
        if (args.length == 1) {
            switch (args[0]) {
                case "1":
                    runSimulation(OrganizationType.FEDERATION, new RescueExpedition());
                    break;
                case "2":
                    runSimulation(OrganizationType.SLAVERY, new ValueOfPI());
                    break;
                case "3":
                    runSimulation(OrganizationType.HIERARCHY, new PublicTransport());
                    break;
                case "4":
                    runSimulation(OrganizationType.COALITION, new PatrolledArea("Kraków"));
                    break;
                default:
                    System.out.println("Bad usage!!");
            }
        } else {
            System.out.println("Executing last problem...");
            runSimulation(OrganizationType.FEDERATION, new RescueExpedition());
        }
    }

    private static void runSimulation(OrganizationType organizationType, Problem problem) {
        Simulator simulator = new Simulator(organizationType, problem);
        simulator.createOrganization();
        simulator.start();
    }
}
