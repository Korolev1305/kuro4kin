package com.company.randomGenerator;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class RadiusGenerator {

    public static int randomRadius(Point center, ArrayList<Point> allPoints, Integer maxRadius, Integer minRadius) {

        int radius = 1;

        Boolean flag = false;

        while (!flag) {

            ArrayList<Point> pointsForCurrentRadius = new ArrayList<>();

            for (int x = center.x - radius; x <= center.x + radius; x++) {
                Double difference = Math.sqrt(Math.pow(radius, 2) - Math.pow(center.x - x, 2));

                Double minValue = center.y - difference;

                Double maxValue = center.y + difference;


                for (int y = (int) Math.round(minValue); y <= (int) Math.round(maxValue); y++) {
                    pointsForCurrentRadius.add(new Point(x, y));
                }

            }
            if (allPoints.containsAll(pointsForCurrentRadius)) {
                if (radius < maxRadius) {
                    radius++;
                } else {
                    flag = true;
                }
            } else {
                radius--;
                flag = true;
            }
        }

        return radius;


    }

    public static int randomSize(Point center, ArrayList<Point> allPoints, Integer maxRadius) {

        Integer size = 1;

        Boolean flag = false;

        while (!flag) {

            ArrayList<Point> pointsForCurrentRadius = new ArrayList<>();

            for (int x = center.x - size; x <= center.x + size; x++) {

                for (int y = center.y - size; y <= center.y + size; y++) {
                    pointsForCurrentRadius.add(new Point(x, y));
                }

            }
            if (allPoints.containsAll(pointsForCurrentRadius)) {
                if (size < maxRadius) {
                    size++;
                } else {
                    flag = true;
                }
            } else {
                size--;
                flag = true;
            }
        }

        return size;


    }

    public static ArrayList<ArrayList<Integer>> randomSizePlusDimension(ArrayList<Integer> center, ArrayList<ArrayList<Integer>> allPoints, Integer maxRadius,Integer signsNumber) {

        Integer size = 1;

        Boolean flag = false;

        ArrayList<ArrayList<Integer>> pointsForCurrentRadius = new ArrayList<>();

        while (!flag) {

            pointsForCurrentRadius = new ArrayList<>();
            ArrayList<Integer> dimensionsMin = new ArrayList<>();
            ArrayList<Integer> dimensionsMax = new ArrayList<>();
            for(Integer point : center){
                dimensionsMin.add(point-size);
                dimensionsMax.add(point+size);
            }
            long counterPoints = 0;
            while (counterPoints < Math.pow(10, signsNumber)) {
                String number = Long.toString(counterPoints);
                if (number.length() < signsNumber) {
                    int n = number.length();
                    for (int i = 0; i < signsNumber - n; i++) {
                        number = "0" + number;
                    }
                }
                char[] array = number.toCharArray();
                ArrayList<Integer> integerArrayList = new ArrayList<>();
                for (char value : array) {
                    integerArrayList.add(Character.getNumericValue(value));
                }
                boolean check = true;
                for(int i=0;i<integerArrayList.size();i++){
                    if((integerArrayList.get(i)>=dimensionsMin.get(i))&&(integerArrayList.get(i)<=dimensionsMax.get(i))){

                    } else {
                        check=false;
                        break;
                    }
                }
                if(check){
                    pointsForCurrentRadius.add(integerArrayList);
                }
                counterPoints++;
            }
            if (allPoints.containsAll(pointsForCurrentRadius)) {
                if (size < maxRadius) {
                    size++;
                } else {
                    flag = true;
                }
            } else {
                size--;
                flag = true;
            }
        }

        return pointsForCurrentRadius;


    }
}
