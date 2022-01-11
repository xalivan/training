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
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
public class PolygonServiceImpl implements PolygonService {
    private final PolygonRepository repository;
    private final ObjectMapper objectMapper;

    @Override
    public List<Polygon> getAll() throws JsonProcessingException {
        List<Polygon> polygons = new ArrayList<>();
        for (PolygonEntity polygonEntity : repository.findAll()) {
            PolygonCoordinates polygonCoordinates = parse(polygonEntity);
            polygons.add(new Polygon(polygonEntity.getId(),
                    polygonEntity.getSquare(),
                    getPoints(polygonCoordinates.getCoordinates())));
        }
        return polygons;
    }

    @Override
    public boolean delete(int id) {
        return repository.delete(id) > 0;
    }

    @Override
    public int save(List<List<Point>> points) {
        return repository.save(new Polygon(points));
    }

    @Override
    public Polygon buffer(int id, double distance) throws JsonProcessingException {
        PolygonEntity polygonEntity = repository.buffer(id, distance);
        PolygonCoordinates polygonCoordinates = parse(polygonEntity);
        return new Polygon(polygonEntity.getId(), polygonEntity.getSquare(), getPoints(polygonCoordinates.getCoordinates()));
    }

    private List<List<Point>> getPoints(List<List<List<Double>>> coordinates) {
        return coordinates.stream()
                .map(PointConverter::convertToPoints)
                .collect(Collectors.toUnmodifiableList());
    }

    private PolygonCoordinates parse(PolygonEntity polygonEntity) throws JsonProcessingException {
        return objectMapper.readValue(polygonEntity.getGeometry(), PolygonCoordinates.class);
    }
}
