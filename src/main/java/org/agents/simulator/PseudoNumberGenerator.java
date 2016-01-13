package org.agents.simulator;

import java.util.Random;

public class PseudoNumberGenerator {
    private static final Random GENERATOR = new Random();

    public static boolean nextBoolean() {
        return GENERATOR.nextBoolean();
    }

    public static int nextInt(int bound) {
        return GENERATOR.nextInt(bound + 1) + 1;
    }

    public static float nextFloat() {
        return GENERATOR.nextFloat();
    }
}
