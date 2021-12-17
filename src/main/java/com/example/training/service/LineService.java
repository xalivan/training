package com.example.training.service;

import com.example.training.model.Line;
import com.example.training.model.utils.Point;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface LineService {
    List<Line> getAll() throws JsonProcessingException;

    boolean delete(int id);

    int save(List<Point> points);
}