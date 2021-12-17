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
    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public List<Line> getAll() throws JsonProcessingException {

           List<Line>lineResponse=new ArrayList<>();
        for (LineEntity lineEntity : repository.findAll()) {
            LineCoordinates point = objectMapper.readValue(lineEntity.getCoordinates(), LineCoordinates.class);
            List<List<Double>> coordinates = point.getCoordinates();
            List<Point> collect = coordinates.stream().map(doubles -> new Point(doubles.get(0), doubles.get(1))).collect(Collectors.toList());
            Line line = new Line(lineEntity.getId(), lineEntity.getDate(), lineEntity.getLength(), collect);
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