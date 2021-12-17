package com.example.training.model;

import com.example.training.model.utils.BasePolygon;
import com.example.training.model.utils.Point;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Getter
public class Polygon extends BasePolygon {

    private final List<Point> points;

    public Polygon(int id, double square, List<Point> points) {
        super(id, square);
        this.points = points;
    }
}
