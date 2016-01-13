package org.agents.simulator.problems.publictransport;

import org.agents.simulator.PseudoNumberGenerator;

import java.util.HashMap;
import java.util.Map;

/**
 * User: Krzysztof Nawrot
 * Date: 2015-11-25.
 */
public class BusLine {
    public static final int LINE_173 = 173;
    public static final int LINE_144 = 144;
    public static final int LINE_501 = 501;
    private  static final int MAX_CAPACITY = PseudoNumberGenerator.nextInt(200);
    static {
        System.out.println("Setting max capacity to " + MAX_CAPACITY);
    }
    private final int lineNumber;
    private final int numOfBuses;
    private Map<Integer, Bus> buses = new HashMap<>();

    public BusLine(int lineNumber, int numOfBuses) {
        this.lineNumber = lineNumber;
        this.numOfBuses = numOfBuses;
    }

    public int createBus(int id) {
        if (buses.size() < numOfBuses) {
            int randomCapacity = PseudoNumberGenerator.nextInt(MAX_CAPACITY);
            buses.put(id, new Bus(randomCapacity, PseudoNumberGenerator.nextInt(randomCapacity)));
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
