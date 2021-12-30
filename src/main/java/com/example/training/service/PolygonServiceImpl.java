package com.example.training.service;

import com.example.training.model.Polygon;
import com.example.training.model.PolygonEntity;
import com.example.training.model.utils.Point;
import com.example.training.model.utils.PolygonCoordinates;
import com.example.training.repository.PolygonRepository;
import com.example.training.service.utils.PointConverter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class PolygonServiceImpl implements PolygonService {
    private final PolygonRepository repository;
    private final ObjectMapper objectMapper;
    private final PointConverter converterPoint;

    @Override
    public List<Polygon> getAll() throws JsonProcessingException {
        List<Polygon> lineList = new ArrayList<>();
        for (PolygonEntity lineEntity : repository.findAll()) {
            lineList.add(parseToPolygon(lineEntity));
        }
        return lineList;
    }

    @Override
    public boolean delete(int id) {
        return repository.delete(id) > 0;
    }

    @Override
    public int save(List<Point> points) {
        return repository.save(converterPoint.setListPointsToString(points));
    }

    @Override
    public int buffer(int id, double distance) {
        return repository.buffer(id, distance);
    }

    private Polygon parseToPolygon(PolygonEntity lineEntity) throws JsonProcessingException {
        PolygonCoordinates parse = parseToPolygonCoordinates(lineEntity);
        List<List<Point>> pointList = converterPoint.convertPointsPolygon(parse.getCoordinates());
        return new Polygon(lineEntity.getId(), lineEntity.getSquare(), pointList);
    }

    private PolygonCoordinates parseToPolygonCoordinates(PolygonEntity lineEntity) throws JsonProcessingException {
        return objectMapper.readValue(lineEntity.getGeometry(), PolygonCoordinates.class);
    }
}