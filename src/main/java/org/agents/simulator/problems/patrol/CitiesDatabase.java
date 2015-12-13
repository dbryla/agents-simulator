package org.agents.simulator.problems.patrol;

import java.util.HashMap;
import java.util.Map;

public class CitiesDatabase {
    private static Map<String, City> database = new HashMap<>();

    static {
        database.put("Kraków",
                new City(
                        new Area("Stare Miasto", "Bracka", "Szpitalna", "Świętego Tomasza"),
                        new Area("Krowodrza", "Urzędnicza", "Budryka", "Królewska", "Podchorążych"),
                        new Area("Prądnik Biały", "Henryka Pachońskiego", "Józefa Mackiewicza")
                )
        );
    }

    public static City getCity(String cityName) {
        City city = database.get(cityName);
        city.reset();
        return city;
    }
}
