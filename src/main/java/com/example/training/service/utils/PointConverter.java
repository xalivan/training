package com.example.training.service.utils;

import com.example.training.model.utils.Point;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toUnmodifiableList;

public class PointConverter {
    public static String convertToString(List<Point> points) {
        return points.stream()
                .map(Point::toString)
                .collect(Collectors.joining(", "));
    }

    public static List<Point> convertToPoints(List<List<Double>> coordinates) {
        return coordinates.stream()
                .filter(doubles -> doubles.size() == 2)
                .map(doubles -> new Point(doubles.get(0),
                        doubles.get(1))).collect(toUnmodifiableList());
    }
}
