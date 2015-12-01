package org.agents.simulator.problems.publictransport;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * User: Krzysztof Nawrot
 * Date: 2015-11-25.
 */
public class BusLine {
    public static final int LINE_173 = 173;
    public static final int LINE_144 = 144;
    public static final int LINE_501 = 501;
    private  static final int MAX_CAPACITY = 50;
    private final int lineNumber;
    private final int numOfBuses;
    private Map<Integer, Bus> buses = new HashMap<>();
    private Random random = new Random();

    public BusLine(int lineNumber, int numOfBuses) {
        this.lineNumber = lineNumber;
        this.numOfBuses = numOfBuses;
    }

    public int createBus(int id) {
        if (buses.size() < numOfBuses) {
            int randomCapacity = random.nextInt(MAX_CAPACITY);
            buses.put(id, new Bus(randomCapacity, random.nextInt(randomCapacity)));
            return id;
        }
        return -1;
    }

    public Bus getBusById(int id) {
        return buses.get(id);
    }

    public int getBusesCount() {
        return buses.size();
    }

    public int getNumOfBuses() {
        return numOfBuses;
    }

    public int getLineNumber() {
        return lineNumber;
    }


}
