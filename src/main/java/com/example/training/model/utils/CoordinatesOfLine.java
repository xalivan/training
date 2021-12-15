package com.example.training.model.utils;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import java.util.List;

@Value
public class CoordinatesOfLine {
    List<List<Double>> coordinates;

    public CoordinatesOfLine(@JsonProperty("coordinates") List<List<Double>> coordinates) {
        this.coordinates = coordinates;
    }
}