package com.example.training.model;

import com.example.training.model.utils.BasePolygon;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode(callSuper = true)
@Getter
public class PolygonEntity extends BasePolygon {
    private final String geometry;

    public PolygonEntity(int id, double square, String geometry) {
        super(id, square);
        this.geometry = geometry;
    }
}
