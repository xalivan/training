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

import static com.example.training.service.utils.PointConverter.convertToPoints;
import static com.example.training.service.utils.PointConverter.convertToCommaSeparatedString;

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
            lineList.add(createLine(lineEntity));
        }
        return lineList;
    }

    @Override
    public boolean delete(int id) {
        return repository.delete(id) > 0;
    }

    @Override
    public int save(List<Point> points) {
        return repository.save(concatLinestring(convertToCommaSeparatedString(points)));
    }

    private String concatLinestring(String points) {
        return "LINESTRING(" + points + ")";
    }

    private Line createLine(LineEntity lineEntity) throws JsonProcessingException {
        return new Line(lineEntity.getId(),
                lineEntity.getDate(),
                lineEntity.getLength(),
                convertToPoints(parse(lineEntity).getCoordinates()));
    }

    private LineCoordinates parse(LineEntity lineEntity) throws JsonProcessingException {
        return objectMapper.readValue(lineEntity.getCoordinates(), LineCoordinates.class);
    }
}
