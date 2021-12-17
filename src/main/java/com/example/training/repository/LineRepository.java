package com.example.training.repository;

import com.example.training.model.LineEntity;

import java.util.List;

public interface LineRepository {

    int save(String geometry);

    int delete(int id);

    List<LineEntity> findAll();
}

