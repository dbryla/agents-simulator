package org.agents.simulator.problems;

public interface Step {
    /**
     * @param if step depends on output from previous one, here it should be given in JSON
     * @return output for next step or result in JSON
     */
    String doIt(String data);
}
