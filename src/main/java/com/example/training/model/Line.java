package com.example.training.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import java.util.List;

@Value
public class Line {

    int length;
    List<Point> points;

    @JsonCreator
    public Line(
            @JsonProperty("length") int length,
            @JsonProperty("points") List<Point> points) {
        this.length = length;
        this.points = points;
    }
}