package com.example.training.repository;

import java.util.List;

public interface LineRepository {

    int save(String geometry);

    int delete(int id);

    List<String> findAllGeom();

    List<Integer> findAllLength();

}

