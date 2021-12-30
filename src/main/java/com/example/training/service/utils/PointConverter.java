package com.example.training.service.utils;

import com.example.training.model.utils.Point;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toUnmodifiableList;

@Service
public class PointConverter {
    public  String setListPointsToString(List<Point> points) {
        return points.stream()
                .map(Point::toString)
                .collect(Collectors.joining(", "));
    }

    public  <E> List<List<Point>> convertPointsPolygon(List<List<List<E>>> coordinates) {
        List<List<Point>> pointListGet = new ArrayList<>();
        for (List<List<E>> coordinate : coordinates) {
            pointListGet.add(convertPointsToLine(coordinate));
        }
        return pointListGet;
    }

    private  <E> List<Point> convertPointsToLine(List<List<E>> coordinates) {
       return coordinates.stream()
                .map(doubles -> new Point((Double) doubles.get(0),
                        (Double) doubles.get(1))).collect(toUnmodifiableList());
    }
}