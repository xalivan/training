package com.example.training.service;

import com.example.training.model.Line;
import com.example.training.model.LineEntity;
import com.example.training.model.utils.Point;
import com.example.training.repository.LineRepository;
import com.example.training.service.utils.ConverterLineEntityToLineResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class LineServiceImpl implements LineService {
    private final LineRepository repository;
    private final ConverterLineEntityToLineResponse converter;

    @Override
    public List<Line> getAll() throws JsonProcessingException {
        List<LineEntity> all = repository.findAll();
        List<Line> lineResponse =new ArrayList<>();
        for (int i = 0; i < all.size(); i++) {
                Line line = converter.doLineResponseFromLineEntity(all.get(i));
                lineResponse.add(line);
        }
        return lineResponse;
    }

    @Override
    public boolean delete(int id) {
        return repository.delete(id) > 0;
    }

    @Override
    public int save(List<Point> points) {
          return repository.save(setListPointsToString(points));
    }

    private String setListPointsToString(List<Point> points) {
        return points.stream()
                .map(Point::toString)
                .collect(Collectors.joining(", "));
    }
}