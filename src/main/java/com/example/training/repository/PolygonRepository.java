package com.example.training.repository;

import com.example.training.model.PolygonEntity;

import java.util.List;

public interface PolygonRepository {
    int save(String points);

    int delete(int id);

    List<PolygonEntity> findAll();

    PolygonEntity buffer(int id, double distance);
}
