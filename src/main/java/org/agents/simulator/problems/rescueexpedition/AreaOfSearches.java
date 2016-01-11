package org.agents.simulator.problems.rescueexpedition;

import java.util.*;

/**
 * User: Krzysztof Nawrot
 * Date: 2016-01-10.
 */
public class AreaOfSearches {
    private static final int ARR_SIZE = 10000;
    private static List<List<Boolean>> areasOfRescueSearches;
    private static int counter = 0;

    public static void setUp() {
        areasOfRescueSearches = new ArrayList<>();
        int count = RescueExpedition.RESCUERS_PER_TEAM_COUNT * RescueExpedition.TEAMS_COUNT;
        Random random = new Random();
        int[] lostArr = new int[count];
        for (int i = 0; i < RescueExpedition.LOST; i++) {
            lostArr[random.nextInt(count)]++;
        }
        for (int i = 0; i < count; i++) {
            List<Boolean> arr = new ArrayList<>(ARR_SIZE);
            for (int j = 0; j < ARR_SIZE; j++) {
                arr.add(false);
            }
            Set<Integer> prevRandoms = new HashSet<>();
            for (int j = 0; j < lostArr[i]; j++) {
                int r = random.nextInt(ARR_SIZE);
                while (prevRandoms.contains(r)) {
                    r = random.nextInt(ARR_SIZE);
                }
                prevRandoms.add(r);
                arr.add(r, true);
            }
            areasOfRescueSearches.add(arr);
        }
    }

    public static List<Boolean> getAreaOfRescueSearches(int i) {
        return areasOfRescueSearches.get(i);
    }

    public static int nextAreaOfSearches() {
        return (counter <= RescueExpedition.RESCUERS_PER_TEAM_COUNT * RescueExpedition.TEAMS_COUNT) ?
                    counter++
                    : -1;
    }
}
