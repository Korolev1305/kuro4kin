package com.company.randomGenerator;

import java.awt.*;
import java.util.concurrent.ThreadLocalRandom;

public class ColorGenerator {

    public static Color randomColor() {
        int r;
        int g;
        int b;
        r = ThreadLocalRandom.current().nextInt(30, 225);
        g = ThreadLocalRandom.current().nextInt(30, 225);
        b = ThreadLocalRandom.current().nextInt(30, 225);
        return new Color(r,g,b);
    }

}
