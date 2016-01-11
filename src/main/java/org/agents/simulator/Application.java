package org.agents.simulator;

import madkit.kernel.AbstractAgent;
import madkit.kernel.Madkit;
import org.agents.simulator.organisations.OrganizationType;
import org.agents.simulator.problems.patrol.PatrolledArea;
import org.agents.simulator.problems.pi.ValueOfPI;
import org.agents.simulator.problems.publictransport.PublicTransport;
import org.agents.simulator.problems.rescueexpedition.RescueExpedition;

public class Application extends AbstractAgent {
    public static void main(String[] args) throws InterruptedException {
        // Run MaDKit with an empty agent to initialize kernel
        AgentsManager.create(new Madkit(new String[] {"--launchAgents org.agents.simulator.Application"}));

        Simulator simulator = new Simulator(OrganizationType.FEDERATION, new RescueExpedition());
        simulator.createOrganization();
        simulator.start();

    }
}
