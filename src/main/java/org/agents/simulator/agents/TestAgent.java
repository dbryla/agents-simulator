package org.agents.simulator.agents;

import madkit.kernel.Agent;

public class TestAgent extends Agent {

    protected void activate() {
        logger.info("Activating agent.");
    }

    protected void live() {
        logger.info("Living and dying...");
    }
}
