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

import static com.example.training.service.utils.ConverterPoint.convertPointsPolygon;
import static com.example.training.service.utils.ConverterPoint.setListPointsToString;

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
            PolygonCoordinates polygonCoordinates = objectMapper.readValue(lineEntity.getGeometry(), PolygonCoordinates.class);
            List<List<Point>> pointList = convertPointsPolygon(polygonCoordinates.getCoordinates());
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
}