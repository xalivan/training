package com.example.training.service;

import com.example.training.model.Line;
import com.example.training.model.Point;

import java.util.List;

public interface LineService {
    List<Line> getAll();
    boolean delete(int id);
    int save(List<Point> pointsy);
}