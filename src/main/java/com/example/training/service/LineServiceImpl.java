package com.example.training.service;

import com.example.training.model.Line;
import com.example.training.model.Point;
import com.example.training.repository.LineRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class LineServiceImpl implements LineService {
    private final LineRepository repository;

    @Override
    public List<Line> getAll() {
        log.info("GeometryServiceImpl.getAll.");
        return repository.findAll();
    }

    @Override
    public boolean delete(int id) {
        log.info("GeometryServiceImpl.delete. id= " + id + "deleted");
        return repository.delete(id) > 0;
    }

    @Override
    public int save(List<Point> points) {
        log.info("GeometryServiceImpl.saveUser.");


        return repository.save(setListPointsToString(points));
    }

    private String setListPointsToString(List<Point> points) {
        return points.stream()
                .map(Point::toString)
                .collect(Collectors.joining(", "));
    }
}