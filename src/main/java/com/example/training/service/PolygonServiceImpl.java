package com.example.training.service;

import com.example.training.model.Polygon;
import com.example.training.model.PolygonEntity;
import com.example.training.model.utils.LineCoordinates;
import com.example.training.model.utils.Point;
import com.example.training.repository.PolygonRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PolygonServiceImpl implements PolygonService {
    private final PolygonRepository repository;
    private final ObjectMapper objectMapper;

    @Override
    public List<Polygon> getAll() throws JsonProcessingException {
        List<Polygon> lineList = new ArrayList<>();
        for (PolygonEntity lineEntity : repository.findAll()) {
            LineCoordinates lineCoordinates = objectMapper.readValue(lineEntity.getGeometry(), LineCoordinates.class);
            List<Point> pointList = convertPoints(lineCoordinates.getCoordinates());
            lineList.add(new Polygon(lineEntity.getId(), lineEntity.getSquare(), pointList));
        }
        return lineList;
    }

    @Override
    public boolean delete(int id) {
        return repository.delete(id) > 0;
    }

    @Override
    public int save(List<Point> points) {
        return repository.save(setListPointsToString(points));
    }

    @Override
    public int buffer(int id, double distance) {
        return repository.buffer(id, distance);
    }

    private String setListPointsToString(List<Point> points) {
        return points.stream()
                .map(Point::toString)
                .collect(Collectors.joining(", "));
    }

    private List<Point> convertPoints(List<List<Double>> coordinates) {
        return coordinates.stream()
                .map(doubles -> new Point(doubles.get(0), doubles.get(1))).collect(Collectors.toUnmodifiableList());
    }
}