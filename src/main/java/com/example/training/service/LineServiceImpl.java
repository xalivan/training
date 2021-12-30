package com.example.training.service;

import com.example.training.model.Line;
import com.example.training.model.LineEntity;
import com.example.training.model.utils.LineCoordinates;
import com.example.training.model.utils.Point;
import com.example.training.repository.LineRepository;
import com.example.training.service.utils.ConverterPoint;
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
public class LineServiceImpl implements LineService {
    private final LineRepository repository;
    private final ObjectMapper objectMapper;
    private  final ConverterPoint converterPoint;

    @Override
    public List<Line> getAll() throws JsonProcessingException {
        List<Line> lineList = new ArrayList<>();
        for (LineEntity lineEntity : repository.findAll()) {
            LineCoordinates lineCoordinates = objectMapper.readValue(lineEntity.getCoordinates(), LineCoordinates.class);
            lineList.add(new Line(lineEntity.getId(), lineEntity.getDate(), lineEntity.getLength(),
                    converterPoint.convertPointsPolygon(List.of(lineCoordinates.getCoordinates())).get(0)));
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

}