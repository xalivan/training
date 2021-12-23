package com.example.training.service.utils;

import com.example.training.model.utils.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ConverterPoint {
   public static String setListPointsToString(List<Point> points) {
        return points.stream()
                .map(Point::toString)
                .collect(Collectors.joining(", "));
    }

    public static List<Point> convertPointsToLine(List<List<Double>> coordinates) {
        return coordinates.stream()
                .map(doubles -> new Point(doubles.get(0), doubles.get(1))).collect(Collectors.toUnmodifiableList());
    }
    public static  List<List<Point>> convertPointsPolygon(List<List<List<Double>>> coordinates) {
        List<List<Point>> pointListGet=new ArrayList<>();
        for (List<List<Double>> coordinate : coordinates) {
            List<Point> pointList = coordinate.stream()
                    .map(doubles -> new Point(doubles.get(0), doubles.get(1))).collect(Collectors.toUnmodifiableList());
            pointListGet.add(pointList);
        }
        return pointListGet;
    }
}
