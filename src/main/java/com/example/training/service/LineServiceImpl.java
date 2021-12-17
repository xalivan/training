package com.example.training.service;

import com.example.training.model.Line;
import com.example.training.model.LineEntity;
import com.example.training.model.utils.LineCoordinates;
import com.example.training.model.utils.Point;
import com.example.training.repository.LineRepository;
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
public class LineServiceImpl implements LineService {
    private final LineRepository repository;
    private final ObjectMapper objectMapper;

    @Override
    public List<Line> getAll() throws JsonProcessingException {
        List<Line> lineList = new ArrayList<>();
        for (LineEntity lineEntity : repository.findAll()) {
            LineCoordinates lineCoordinates = objectMapper.readValue(lineEntity.getCoordinates(), LineCoordinates.class);
            List<Point> pointList = convertPoints(lineCoordinates.getCoordinates());
            lineList.add(new Line(lineEntity.getId(), lineEntity.getDate(), lineEntity.getLength(), pointList));
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