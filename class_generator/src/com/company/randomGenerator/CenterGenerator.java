package com.company.randomGenerator;

import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class CenterGenerator {

    public static Point randomCenter(ArrayList<Point> allPoints) {
        Point center;

        center = allPoints.get(ThreadLocalRandom.current().nextInt(allPoints.size()));

        return center;
    }

    public static ArrayList<Integer> randomCenterPlusDimension(ArrayList<ArrayList<Integer>> allPoints, Integer singsNumber) {
        ArrayList<Integer> center;

        center = allPoints.get(ThreadLocalRandom.current().nextInt(allPoints.size()));

        return center;
    }
}
