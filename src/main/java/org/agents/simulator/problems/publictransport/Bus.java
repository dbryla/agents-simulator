package org.agents.simulator.problems.publictransport;

/**
 * Created by SG0221985 on 2015-11-25.
 */
public class Bus {
    private final int capacity;
    private final int numOfPassengers;

    public Bus(int capacity, int numOfPassengers) {
        this.capacity = capacity;
        this.numOfPassengers = numOfPassengers;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getNumOfPassengers() {
        return numOfPassengers;
    }
}
