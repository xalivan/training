package com.example.training.service;

import com.example.training.model.Polygon;
import com.example.training.model.utils.Point;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface PolygonService {
    List<Polygon> getAll() throws JsonProcessingException;

    boolean delete(int id);

    int save(List<List<Point>> points);

    Polygon buffer(int id, double distance) throws JsonProcessingException;
}


