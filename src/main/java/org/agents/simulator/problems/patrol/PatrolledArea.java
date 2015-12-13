package org.agents.simulator.problems.patrol;

import org.agents.simulator.problems.Problem;
import org.agents.simulator.problems.Step;
import org.codehaus.jackson.map.ObjectMapper;

import java.util.ArrayList;
import java.util.Random;

public class PatrolledArea implements Problem {

    private static final ArrayList<Step> STEPS = new ArrayList<>();
    private static final Random GENERATOR = new Random();
    private final String cityName;

    public PatrolledArea(String cityName) {
        this.cityName = cityName;
        City city = CitiesDatabase.getCity(cityName);
        STEPS.add(data -> city.getArea(data).getRemainingStreet());
        STEPS.add(data -> GENERATOR.nextBoolean() ? "true" : "false");
    }

    @Override
    public ArrayList<Step> getListOfSteps() {
        return STEPS;
    }

    @Override
    public void announceResult(Object finalResult) {
        System.out.println(finalResult);
    }

    public String getCityName() {
        return cityName;
    }
}
