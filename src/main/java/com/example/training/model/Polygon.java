package com.example.training.model;

import com.example.training.model.utils.BasePolygon;
import com.example.training.model.utils.Point;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@EqualsAndHashCode(callSuper = true)
@Getter
public class Polygon extends BasePolygon {
    private final List<List<Point>> points;

    public Polygon(int id, double square, List<List<Point>> points) {
        super(id, square);
        this.points = points;
    }

    public Polygon(List<List<Point>> points) {
        this.points = points;
    }

    public String toWKTString() {
        List<String> stringList = new ArrayList<>();
        for (List<Point> point : points) {
            stringList.add(convertToString(point));
        }
        return addPolygonString(stringList);
    }

    private String convertToString(List<Point> points) {
        return points.stream()
                .map(Point::toString)
                .collect(Collectors.joining(", ", "(", ")"));
    }

    private String addPolygonString(List<String> stringList) {
        return stringList.stream()
                .collect(Collectors.joining(",", "POLYGON(", ")"));
    }
}
