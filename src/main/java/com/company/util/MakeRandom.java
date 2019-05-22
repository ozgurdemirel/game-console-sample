package com.company.util;

import java.util.Random;


/**
 * wrapper random class for test purpose
 */
public final class MakeRandom {

    private static MakeRandom instance;
    private Random random = new Random();

    private MakeRandom() {
    }

    public static synchronized MakeRandom getInstance() {
        if (instance != null) {
            return instance;
        }
        return instance = new MakeRandom();
    }

    public int get(int bound) {
        return random.nextInt(bound);
    }

}
