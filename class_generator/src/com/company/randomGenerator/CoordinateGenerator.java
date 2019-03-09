package com.company.randomGenerator;

import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class CoordinateGenerator {

    public static Point radnomCoordinateCircle(Point centerCoordinate, int radius){
        Point randomPoint = new Point();

        int x,y;

        if(radius>=1){
            x = ThreadLocalRandom.current().nextInt(centerCoordinate.x-radius,centerCoordinate.x+radius);
        } else {
            x = centerCoordinate.x;
        }

        Double difference = Math.sqrt(Math.pow(radius,2)-Math.pow(centerCoordinate.x-x,2));

        Double minValue = centerCoordinate.y - difference;

        Double maxValue = centerCoordinate.y + difference;

        if(maxValue.intValue()-minValue.intValue()<=1) {

            y = (int) Math.round(minValue);

        } else {
            y = ThreadLocalRandom.current().nextInt((int) Math.round(minValue), (int) Math.round(maxValue));
        }

        randomPoint.x = x;

        randomPoint.y = y;

        return randomPoint;
    }

    public static Point radnomCoordinateSquare(Point centerCoordinate, int size){
        Point randomPoint = new Point();

        int x,y;

        if(size>=1){
            x = ThreadLocalRandom.current().nextInt(centerCoordinate.x-size,centerCoordinate.x+size);
        } else {
            x = centerCoordinate.x;
        }

        if(size>=1){
            y = ThreadLocalRandom.current().nextInt(centerCoordinate.y-size,centerCoordinate.y+size);
        } else {
            y = centerCoordinate.y;
        }

        randomPoint.x = x;

        randomPoint.y = y;

        return randomPoint;
    }

    /*public static ArrayList<Integer> radnomCoordinateSquarePlusDimension(ArrayList<Integer> centerCoordinate, int size, Integer signsNumber){
        ArrayList<Integer> randomPoint = new ArrayList<>();

        int x,y;

        if(size>=1){
            x = ThreadLocalRandom.current().nextInt(centerCoordinate.x-size,centerCoordinate.x+size);
        } else {
            x = centerCoordinate.x;
        }

        if(size>=1){
            y = ThreadLocalRandom.current().nextInt(centerCoordinate.y-size,centerCoordinate.y+size);
        } else {
            y = centerCoordinate.y;
        }

        randomPoint.x = x;

        randomPoint.y = y;

        return randomPoint;
    }*/
}
