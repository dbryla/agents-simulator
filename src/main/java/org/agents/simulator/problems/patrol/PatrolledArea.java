package org.agents.simulator.problems.patrol;

import org.agents.simulator.PseudoNumberGenerator;
import org.agents.simulator.problems.Problem;
import org.agents.simulator.problems.Step;

import java.util.ArrayList;

public class PatrolledArea implements Problem {

    private static final ArrayList<Step> STEPS = new ArrayList<>();
    private final String cityName;

    public PatrolledArea(String cityName) {
        this.cityName = cityName;
        City city = CitiesDatabase.getCity(cityName);
        STEPS.add(data -> city.getArea(data).getRemainingStreet());
        STEPS.add(data -> PseudoNumberGenerator.nextBoolean() ? "true" : "false");
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
