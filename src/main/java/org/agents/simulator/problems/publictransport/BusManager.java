package org.agents.simulator.problems.publictransport;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * User: Krzysztof Nawrot
 * Date: 2015-11-25.
 */
public class BusManager {
    public static final int NUM_OF_BUSES = 5;
    private static final AtomicInteger busIdCounter = new AtomicInteger(0);
    private static Map<Integer, BusLine> busLines = new HashMap<>();

    public static void createBusLine(int lineNum) {
        busLines.put(lineNum, new BusLine(lineNum, NUM_OF_BUSES));
    }

    public static int createBusOnLine(int lineNum) {
        BusLine busLine = busLines.get(lineNum);
        if ((busLine!= null) && (busLine.getBusesCount() < busLine.getNumOfBuses())) {
            int id = busIdCounter.getAndIncrement();
            busLine.createBus(id);
            return id;
        }
        return -1;
    }

    public static Bus getBusById(int id) {
        for (BusLine busLine : busLines.values()) {
            Bus bus = busLine.getBusById(id);
            if (bus != null) {
                return bus;
            }
        }
        return null;
     }

    public static int getNumberOfBusesOnLine(int line) {
        return busLines.get(line).getNumOfBuses();
    }
}
