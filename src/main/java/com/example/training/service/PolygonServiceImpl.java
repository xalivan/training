package com.example.training.service;

import com.example.training.model.Polygon;
import com.example.training.model.PolygonEntity;
import com.example.training.model.utils.Point;
import com.example.training.model.utils.PolygonCoordinates;
import com.example.training.repository.PolygonRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.example.training.service.utils.PointConverter.convertToPointList;
import static com.example.training.service.utils.PointConverter.convertToString;

@Slf4j
@Service
@RequiredArgsConstructor
public class PolygonServiceImpl implements PolygonService {
    private final PolygonRepository repository;
    private final ObjectMapper objectMapper;

    @Override
    public List<Polygon> getAll() throws JsonProcessingException {
        List<Polygon> polygonList = new ArrayList<>();
        for (PolygonEntity polygonEntity : repository.findAll()) {
            polygonList.add(createPolygon(polygonEntity));
        }
        return polygonList;
    }

    @Override
    public boolean delete(int id) {
        return repository.delete(id) > 0;
    }

    @Override
    public int save(List<Point> points) {
        return repository.save(convertToString(points));
    }

    @Override
    public int buffer(int id, double distance) {
        return repository.buffer(id, distance);
    }

    private Polygon createPolygon(PolygonEntity lineEntity) throws JsonProcessingException {
        PolygonCoordinates polygonCoordinates = parse(lineEntity);
        List<List<Point>> pointList = new ArrayList<>();
        for (int i = 0; i < polygonCoordinates.getCoordinates().size(); i++) {
            pointList.add(convertToPointList(polygonCoordinates.getCoordinates().get(i)));
        }
        return new Polygon(lineEntity.getId(), lineEntity.getSquare(), pointList);
    }

    private PolygonCoordinates parse(PolygonEntity polygonEntity) throws JsonProcessingException {
        return objectMapper.readValue(polygonEntity.getGeometry(), PolygonCoordinates.class);
    }
}
