package org.agents.simulator.problems.rescueexpedition;

import org.agents.simulator.PseudoNumberGenerator;
import org.agents.simulator.problems.Problem;
import org.agents.simulator.problems.Step;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Krzysztof Nawrot
 * Date: 2016-01-10.
 */
public class RescueExpedition implements Problem {
    public static final int TEAMS_COUNT = 3;
    public static final int RESCUERS_PER_TEAM_COUNT = PseudoNumberGenerator.nextInt(10);
    public static final int LOST = PseudoNumberGenerator.nextInt(10);
    private static final ArrayList<Step> STEPS = new ArrayList<>();
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public RescueExpedition() {
        System.out.println("Setting rescuers per team to " + RESCUERS_PER_TEAM_COUNT);
        System.out.println("Setting lost person to " + LOST);
        STEPS.add(data -> {
            //try to find lost people in my area
            List<Boolean> area = AreaOfSearches.getAreaOfRescueSearches(new Integer(data));
            int foundLostCount = 0;
            for (boolean foundLost : area) {
                if (foundLost) {
                    foundLostCount++;
                }
            }
            return Integer.toString(foundLostCount);
        });
        STEPS.add(data -> {
            //prepare info for team report
            String[] result = data.split(":");
            try {
                int foundInMyTeam = new Integer(result[1]);
                StringBuilder sb = new StringBuilder("\n----- Report for team " + result[0] + " -----\n");
                sb.append("My team found ");
                sb.append(foundInMyTeam);
                sb.append(" out of ");
                sb.append(LOST);
                sb.append(" lost people.");
                sb.append("\n");
                return OBJECT_MAPPER.writeValueAsString(sb);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        });
    }
    @Override
    public ArrayList<Step> getListOfSteps() {
        return STEPS;
    }

    @Override
    public void announceResult(Object finalResult) {
        System.out.println(((String) finalResult).replaceAll("\\\\n", "\n"));
    }
}
