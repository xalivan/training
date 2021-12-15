package com.example.training.model;

import com.example.training.model.utils.BaseModel;
import com.example.training.model.utils.Point;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.List;
@EqualsAndHashCode(callSuper = true)
@Value
public class Line extends BaseModel {

    List<Point> points;

    public Line(int id, LocalDateTime date, int length, List<Point> points) {
        super(id, date, length);
        this.points = points;
    }
}

