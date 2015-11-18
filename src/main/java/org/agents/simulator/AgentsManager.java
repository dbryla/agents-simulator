package org.agents.simulator;

import madkit.action.KernelAction;
import madkit.kernel.Agent;
import madkit.kernel.Madkit;

public class AgentsManager {
    private static Madkit madkit;

    public static void create(Madkit madkit) {
        AgentsManager.madkit = madkit;
    }

    public static void startAgent(Agent agent) {
        madkit.doAction(KernelAction.LAUNCH_AGENT, agent, false);
    }
}
