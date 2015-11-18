package org.agents.simulator.problems;

public interface Step {

    default boolean isFinalStep() {
        return false;
    }

    default boolean dependsOnPreviousOutput() {
        return false;
    }

    /**
     * @param if step depends on output from previous one, here it should be given in JSON
     * @return output for next step or result in JSON
     */
    String doIt(String data);
}
