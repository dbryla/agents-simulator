package org.agents.simulator.problems.publictransport;

import org.agents.simulator.problems.Problem;
import org.agents.simulator.problems.Step;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.*;

/**
 * User: Krzysztof Nawrot
 * Date: 2015-11-25.
 */
public class PublicTransport implements Problem {
    private static final ArrayList<Step> STEPS = new ArrayList<>();
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public PublicTransport() {
        STEPS.add(data -> {
            //count number of passengers in a bus and return it
            Bus bus = BusManager.getBusById(Integer.valueOf(data));
            return "{ \"numOfPassengers\": " + bus.getNumOfPassengers() + ", \"capacity\": " + bus.getCapacity() + "}";
        });
        STEPS.add(data -> {
            //gather info about buses on line and return it
            double sumOfOvercrowdingOnLine = 0;
            try {
                JsonNode tree = OBJECT_MAPPER.readTree(data).get("data");
                Iterator<JsonNode> treeElements = tree.getElements();
                JsonNode node = treeElements.next();
                int lineNum = node.get("lineNum").getIntValue();
                int numOfBuses = node.get("numOfBuses").getIntValue();
                while (treeElements.hasNext()) {
                    node = treeElements.next();
                    sumOfOvercrowdingOnLine += new Double(node.get("numOfPassengers").getIntValue())
                                                        / node.get("capacity").getIntValue();
                }
                return "{ \"lineNum\": " + lineNum + ", \"overcrowding\": " + sumOfOvercrowdingOnLine / numOfBuses  + "}";
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        });
        STEPS.add(data -> {
            //gather all info from bus lines and prepare overall report
            double totalOvercrowding = 0.0;
            double highestOvercrowding = 0.0;
            int lineWithHighestOvercrowding = 0;
            int linesNum = 0;
            try {
                JsonNode tree = OBJECT_MAPPER.readTree(data).get("data");
                Iterator<JsonNode> treeElements = tree.getElements();
                while (treeElements.hasNext()) {
                    JsonNode node = treeElements.next();
                    double overcrowding = node.get("overcrowding").getDoubleValue();
                    if (overcrowding > highestOvercrowding) {
                        lineWithHighestOvercrowding = node.get("lineNum").getIntValue();
                        highestOvercrowding = overcrowding;
                    }
                    totalOvercrowding += overcrowding;
                    linesNum++;
                }
                StringBuilder result = new StringBuilder("\n\n----- REPORT OF SCAN -----\n");
                result.append("Average overcrowding: ");
                result.append(totalOvercrowding / linesNum);
                result.append("\n");
                result.append("Highest overcrowding: ");
                result.append(highestOvercrowding);
                result.append(" on line number: ");
                result.append(lineWithHighestOvercrowding);
                result.append("\n\n");
                return OBJECT_MAPPER.writeValueAsString(result);
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
