package com.company;

import com.company.randomGenerator.CenterGenerator;
import com.company.randomGenerator.ColorGenerator;
import com.company.randomGenerator.CoordinateGenerator;
import com.company.randomGenerator.RadiusGenerator;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Main extends Canvas {

    private static int coordinateDistance = 5;

    private static Boolean isDevided = true;

    private static Integer classNumber = 100;

    private static Integer pointForClass = 1000;

    private static Integer maxRadius = -1;

    private static Integer crossNumber = -1;

    private static Integer signsNumber = 2;

    public static void main(String[] args) {


        Scanner in = new Scanner(System.in);

        System.out.println("Enter isDevided: ");

        isDevided = in.nextBoolean();

        System.out.println("Enter class number: ");

        classNumber = in.nextInt();

        System.out.println("Enter points for class: ");

        pointForClass = in.nextInt();

        System.out.println("Enter max raidus (write -1 if you want classes without current max raidus: ");

        maxRadius = in.nextInt();

        System.out.println("Enter dimensions number: ");

        signsNumber = in.nextInt();

        if (!isDevided) {
            System.out.println("Enter cross number (write -1 if you want classes without current cross number: ");

            crossNumber = in.nextInt();
        }


        JFrame w = new JFrame("Окно с изображением");
        w.setSize(100 * coordinateDistance, 100 * coordinateDistance);
        w.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Main main = new Main();
        w.add(main);

        w.setVisible(true);
    }

    public void generateClasses(Graphics g, boolean isDevided, int classNumber, int pointForOneClass, int coordinateDistance, boolean isCircle, Integer maxRadius) {

        ArrayList<ArrayList<Point>> arrayClassesWithPoints = new ArrayList<>();
        ArrayList<Point> pointArrayList = new ArrayList<>();
        for (int i = 0; i <= 100; i += 1)
            for (int j = 0; j <= 100; j += 1) {
                pointArrayList.add(new Point(i, j));
            }

        for (int k = 0; k < classNumber; k++) {

            Point center = null;

            int radius = 0;

            ArrayList<Point> classPoints = new ArrayList<>();

            if (isCircle) {
                center = CenterGenerator.randomCenter(pointArrayList);

                if (isDevided) {
                    if (maxRadius != -1)
                        radius = RadiusGenerator.randomRadius(center, pointArrayList, maxRadius,10);
                    else {
                        radius = RadiusGenerator.randomRadius(center, pointArrayList, 40,10);
                    }
                } else {
                    if (maxRadius != -1) {
                        radius = ThreadLocalRandom.current().nextInt(1, maxRadius);
                    } else {
                        radius = ThreadLocalRandom.current().nextInt(40);
                    }
                }

                classPoints.add(center);

                for (int i = 0; i < pointForOneClass; i++) {
                    classPoints.add(CoordinateGenerator.radnomCoordinateCircle(center, radius));
                }

            } else {
                center = CenterGenerator.randomCenter(pointArrayList);

                if (isDevided) {
                    if (maxRadius != -1)
                        radius = RadiusGenerator.randomSize(center, pointArrayList, maxRadius);
                    else {
                        radius = RadiusGenerator.randomSize(center, pointArrayList, 100);
                    }
                } else {
                    if (maxRadius != -1) {
                        radius = ThreadLocalRandom.current().nextInt(1, maxRadius);
                    } else {
                        radius = ThreadLocalRandom.current().nextInt(40);
                    }
                }
                classPoints.add(center);

                for (int i = 0; i < pointForOneClass; i++) {
                    classPoints.add(CoordinateGenerator.radnomCoordinateSquare(center, radius));
                }

            }

            arrayClassesWithPoints.add(classPoints);

            ArrayList<Point> pointsForCurrentRadius = new ArrayList<>();

            if (isCircle) {

                for (int x = center.x - radius; x <= center.x + radius; x++) {
                    Double difference = Math.sqrt(Math.pow(radius, 2) - Math.pow(center.x - x, 2));

                    Double minValue = center.y - difference;

                    Double maxValue = center.y + difference;

                    for (int y = (int) Math.round(minValue) - 1; y <= (int) Math.round(maxValue) + 1; y++) {
                        pointsForCurrentRadius.add(new Point(x, y));
                    }

                }
            } else {

                for (int x = center.x - radius; x <= center.x + radius; x++) {

                    for (int y = center.y - radius; y <= center.y + radius; y++) {
                        pointsForCurrentRadius.add(new Point(x, y));
                    }
                }
            }

            if (isDevided == true) {
                pointArrayList.removeAll(pointsForCurrentRadius);
            }
        }

        arrayClassesWithPoints.forEach(points -> {
            g.setColor(ColorGenerator.randomColor());

            points.forEach(point -> {
                g.drawOval(point.x * coordinateDistance, point.y * coordinateDistance, 1 * coordinateDistance / 2, coordinateDistance / 2);
            });
        });

    }

    public void paint(Graphics g) {

        if (signsNumber == 2) {
            if (crossNumber == -1) {
                generateClasses(g, isDevided, classNumber, pointForClass, coordinateDistance, true, maxRadius);
            } else {
                ArrayList<ArrayList<Point>> arrayClassesWithPoints = null;
                while (arrayClassesWithPoints == null) {
                    arrayClassesWithPoints = generateClassesWithCross(classNumber, pointForClass, maxRadius, crossNumber);
                }
                arrayClassesWithPoints.forEach(points ->

                {
                    g.setColor(ColorGenerator.randomColor());

                    points.forEach(point -> {
                        g.drawOval(point.x * coordinateDistance, point.y * coordinateDistance, 1 * coordinateDistance / 2, coordinateDistance / 2);
                    });
                });
            }
        } else {
            ArrayList<ArrayList<ArrayList<Integer>>> arrayClassesWithPoints = new ArrayList<>();
            if (crossNumber == -1) {
                arrayClassesWithPoints = generateClassesWithMoreSigns(isDevided,classNumber,pointForClass,maxRadius,signsNumber);

            } else {
                arrayClassesWithPoints = generateClassesWithCrossMoreSigns(classNumber,pointForClass,maxRadius,crossNumber,signsNumber);
            }
        }

        g.dispose();

    }

    private ArrayList<ArrayList<ArrayList<Integer>>> generateClassesWithMoreSigns(boolean isDevided, int classNumber, int pointForOneClass, Integer maxRadius, Integer signsNumber) {
        ArrayList<ArrayList<ArrayList<Integer>>> arrayClassesWithPoints = new ArrayList<>();
        Integer counter = 0;
        long counterPoints = 0;
        Double count = Math.pow(10, signsNumber);
        long l = count.longValue();
        ArrayList<ArrayList<Integer>> pointArrayList = new ArrayList<>();
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
            pointArrayList.add(integerArrayList);
            counterPoints++;
        }

        for (int k = 0; k < classNumber; k++) {

            ArrayList<Integer> center = null;

            int radius = 0;

            ArrayList<ArrayList<Integer>> classPoints = new ArrayList<>();

            ArrayList<ArrayList<Integer>> pointsForCurrentRadius = new ArrayList<>();

            if (pointArrayList.size() == 0) {
                System.out.println("dont't have points");
                break;
            }

            center = CenterGenerator.randomCenterPlusDimension(pointArrayList,signsNumber);

            if (isDevided) {
                if (maxRadius != -1)
                    pointsForCurrentRadius = RadiusGenerator.randomSizePlusDimension(center, pointArrayList, maxRadius,signsNumber);
                else {
                    pointsForCurrentRadius = RadiusGenerator.randomSizePlusDimension(center, pointArrayList, 10,signsNumber);
                }
            } else {
                if (maxRadius != -1) {
                    pointsForCurrentRadius = RadiusGenerator.randomSizePlusDimension(center, pointArrayList, maxRadius,signsNumber);
                } else {
                    pointsForCurrentRadius = RadiusGenerator.randomSizePlusDimension(center, pointArrayList, 10,signsNumber);
                }
            }
            classPoints.add(center);

            for (int i = 0; i < pointForOneClass; i++) {
                classPoints.add(CenterGenerator.randomCenterPlusDimension(pointsForCurrentRadius,signsNumber));
            }


            arrayClassesWithPoints.add(classPoints);

            if (isDevided == true) {
                pointArrayList.removeAll(pointsForCurrentRadius);
            }
            System.out.println(k);
        }
        return arrayClassesWithPoints;
    }

    private ArrayList<ArrayList<ArrayList<Integer>>> generateClassesWithCrossMoreSigns(int classNumber, int pointForOneClass, Integer maxRadius, Integer crossNumber, Integer signsNumber) {

        Boolean flagRemoveCross = false;

        Integer crossCounter = 0;

        long counterPoints = 0;
        Double count = Math.pow(10, signsNumber);
        long l = count.longValue();

        ArrayList<ArrayList<Integer>> pointInCrossing = new ArrayList<>();

        ArrayList<ArrayList<ArrayList<Integer>>> pointInCrossing2 = new ArrayList<>();

        ArrayList<ArrayList<ArrayList<Integer>>> arrayClassesWithPoints = new ArrayList<>();
        ArrayList<ArrayList<Integer>> pointArrayList = new ArrayList<>();
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
            pointArrayList.add(integerArrayList);
            counterPoints++;
        }

        boolean flag = false;

        for (int k = 0; k < classNumber; k++) {

            ArrayList<Integer> center = null;

            int radius = 0;

            ArrayList<ArrayList<Integer>> classPoints = new ArrayList<>();

            ArrayList<ArrayList<Integer>> pointsForCurrentRadius = new ArrayList<>();

            if (pointArrayList.size() == 0) {
                System.out.println("dont't have points");
                break;
            }

            center = CenterGenerator.randomCenterPlusDimension(pointArrayList,signsNumber);

            if (crossCounter == crossNumber) {
                if (maxRadius != -1)
                    pointsForCurrentRadius = RadiusGenerator.randomSizePlusDimension(center, pointArrayList, maxRadius,signsNumber);
                else {
                    pointsForCurrentRadius = RadiusGenerator.randomSizePlusDimension(center, pointArrayList, 5,signsNumber);
                }
            } else {
                if (maxRadius != -1) {
                    pointsForCurrentRadius = RadiusGenerator.randomSizePlusDimension(center, pointArrayList, maxRadius,signsNumber);
                } else {
                    pointsForCurrentRadius = RadiusGenerator.randomSizePlusDimension(center, pointArrayList, 5,signsNumber);
                }
            }

            classPoints.add(center);

            for (int i = 0; i < pointForOneClass; i++) {
                classPoints.add(CenterGenerator.randomCenterPlusDimension(pointsForCurrentRadius,signsNumber));
            }

            if ((crossCounter < crossNumber) && (pointInCrossing2.size() > 0)) {
                ArrayList<Integer> counter = new ArrayList<>();
                pointInCrossing2.forEach(points -> {
                    counter.add(0);
                });
                for (ArrayList<Integer> point : classPoints) {
                    for (ArrayList<ArrayList<Integer>> points : pointInCrossing2) {
                        if (counter.get(pointInCrossing2.indexOf(points)) != 1) {
                            if (points.contains(point)) {
                                crossCounter++;
                                counter.set(pointInCrossing2.indexOf(points), 1);
                            }
                        } else {
                            continue;
                        }
                    }

                }

            }

            System.out.println(crossCounter);
            if ((!flag) && (crossCounter == crossNumber)) {
                flag = true;
                System.out.println("Cross count: " + crossCounter);
            }
            if (crossCounter > crossNumber) {
                System.out.println(crossCounter);
                return null;
            } else {

                arrayClassesWithPoints.add(classPoints);

                if (crossCounter < crossNumber) {
                    pointInCrossing.addAll(pointsForCurrentRadius);
                    pointInCrossing2.add(pointsForCurrentRadius);
                }

                if (crossCounter == crossNumber) {
                    pointArrayList.removeAll(pointsForCurrentRadius);
                }

                if (crossCounter == crossNumber) {
                    if (!flagRemoveCross) {
                        pointArrayList.removeAll(pointInCrossing);
                        pointInCrossing.clear();
                        flagRemoveCross = true;
                    }
                }
            }
        }
        if(crossCounter!=crossNumber){
            System.out.println(crossCounter);
            return null;
        } else {
            return arrayClassesWithPoints;
        }
    }


    private ArrayList<ArrayList<Point>> generateClassesWithCross(int classNumber, int pointForOneClass, Integer maxRadius, Integer crossNumber) {

        Boolean flagRemoveCross = false;

        Integer crossCounter = 0;

        ArrayList<Point> pointInCrossing = new ArrayList<>();

        ArrayList<ArrayList<Point>> pointInCrossing2 = new ArrayList<>();

        ArrayList<ArrayList<Point>> arrayClassesWithPoints = new ArrayList<>();
        ArrayList<Point> pointArrayList = new ArrayList<>();
        for (int i = 0; i <= 100; i += 1)
            for (int j = 0; j <= 100; j += 1) {
                pointArrayList.add(new Point(i, j));
            }

        boolean flag = false;

        for (int k = 0; k < classNumber; k++) {

            Point center = null;

            int radius = 0;

            ArrayList<Point> classPoints = new ArrayList<>();

            if (pointArrayList.size() == 0) {
                System.out.println("dont't have points");
                break;
            }

            center = CenterGenerator.randomCenter(pointArrayList);

            if (crossCounter == crossNumber) {
                if (maxRadius != -1)
                    radius = RadiusGenerator.randomRadius(center, pointArrayList, maxRadius,10);
                else {
                    radius = RadiusGenerator.randomRadius(center, pointArrayList, 30,10);
                }
            } else {
                if (maxRadius != -1) {
                    radius = ThreadLocalRandom.current().nextInt(0, maxRadius);
                } else {
                    radius = ThreadLocalRandom.current().nextInt(0, 30);
                }
            }

            classPoints.add(center);

            for (int i = 0; i < pointForOneClass; i++) {
                classPoints.add(CoordinateGenerator.radnomCoordinateCircle(center, radius));
            }

            if ((crossCounter < crossNumber) && (pointInCrossing2.size() > 0)) {
                ArrayList<Integer> counter = new ArrayList<>();
                pointInCrossing2.forEach(points -> {
                    counter.add(0);
                });
                for (Point point : classPoints) {
                    for (ArrayList<Point> points : pointInCrossing2) {
                        if (counter.get(pointInCrossing2.indexOf(points)) != 1) {
                            if (points.contains(point)) {
                                crossCounter++;
                                counter.set(pointInCrossing2.indexOf(points), 1);
                            }
                        } else {
                            continue;
                        }
                    }

                }

            }

            if ((!flag) && (crossCounter == crossNumber)) {
                flag = true;
                System.out.println("Cross count: " + crossCounter);
            }
            if (crossCounter > crossNumber) {
                System.out.println(crossCounter);
                return null;
            } else {

                arrayClassesWithPoints.add(classPoints);

                ArrayList<Point> pointsForCurrentRadius = new ArrayList<>();


                for (int x = center.x - radius; x <= center.x + radius; x++) {
                    Double difference = Math.sqrt(Math.pow(radius, 2) - Math.pow(center.x - x, 2));

                    Double minValue = center.y - difference;

                    Double maxValue = center.y + difference;

                    for (int y = (int) Math.round(minValue); y <= (int) Math.round(maxValue); y++) {
                        pointsForCurrentRadius.add(new Point(x, y));

                    }

                }
                if (crossCounter < crossNumber) {
                    pointInCrossing.addAll(pointsForCurrentRadius);
                    pointInCrossing2.add(pointsForCurrentRadius);
                }

                if (crossCounter == crossNumber) {
                    pointArrayList.removeAll(pointsForCurrentRadius);
                }

                if (crossCounter == crossNumber) {
                    if (!flagRemoveCross) {
                        pointArrayList.removeAll(pointInCrossing);
                        pointInCrossing.clear();
                        flagRemoveCross = true;
                    }
                }
            }
        }
        if(crossCounter!=crossNumber){
            System.out.println(crossCounter);
            return null;
        } else {
            return arrayClassesWithPoints;
        }
    }


}
