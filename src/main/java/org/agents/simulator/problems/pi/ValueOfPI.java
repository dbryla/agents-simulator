package org.agents.simulator.problems.pi;

import org.agents.simulator.PseudoNumberGenerator;
import org.agents.simulator.problems.Problem;
import org.agents.simulator.problems.Step;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class ValueOfPI implements Problem {
    private static final ArrayList<Step> STEPS = new ArrayList<>();

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private static final int POINTS_LIMIT = PseudoNumberGenerator.nextInt(1000000);

    static {
        System.out.println("Setting points limit to " + POINTS_LIMIT);
        STEPS.add(data -> {
            int circleArea = 0;
            int squareArea = 0;
            for (int i = 0; i < POINTS_LIMIT; ++i) {
                float x = PseudoNumberGenerator.nextFloat();
                float y = PseudoNumberGenerator.nextFloat();
                if (withinCircle(x, y)) {
                    circleArea++;
                }
                squareArea++;
            }
            return "{ \"circleArea\": " + circleArea + ", \"squareArea\": " + squareArea + "}";
        });

        STEPS.add(new Step() {

            @Override
            public String doIt(String data) {
                int circleArea = 0;
                int squareArea = 0;
                try {
                    JsonNode tree = OBJECT_MAPPER.readTree(data).get("data");
                    Iterator<JsonNode> treeElements = tree.getElements();
                    while(treeElements.hasNext()) {
                        JsonNode node = treeElements.next();
                        circleArea += node.get("circleArea").getIntValue();
                        squareArea += node.get("squareArea").getIntValue();
                    }
                    return OBJECT_MAPPER.writeValueAsString((4.0 * circleArea) / squareArea);
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        });
    }

    private static boolean withinCircle(float x, float y) {
        return Float.compare(x * x + y * y, 1.0f) <= 0;
    }

    @Override
    public ArrayList<Step> getListOfSteps() {
        return STEPS;
    }

    @Override
    public void announceResult(Object finalResult) {
        System.out.println("PI: " + finalResult);
    }

}
