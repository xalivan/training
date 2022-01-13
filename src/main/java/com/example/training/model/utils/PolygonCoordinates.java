package com.example.training.model.utils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import java.util.List;

@Value
@JsonIgnoreProperties(ignoreUnknown = true)
public class PolygonCoordinates {
    List<List<List<Double>>> coordinates;

    public PolygonCoordinates(@JsonProperty("coordinates") List<List<List<Double>>> coordinates) {
        this.coordinates = coordinates;
    }
}
