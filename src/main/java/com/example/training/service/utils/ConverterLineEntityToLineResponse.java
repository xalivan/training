package com.example.training.service.utils;

import com.example.training.model.Line;
import com.example.training.model.LineEntity;
import com.example.training.model.utils.CoordinatesOfLine;
import com.example.training.model.utils.Point;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Component
public class ConverterLineEntityToLineResponse {

    private final ObjectMapper objectMapper;

    public Line doLineResponseFromLineEntity(LineEntity lineEntity) throws JsonProcessingException {
        return new Line(lineEntity.getId(),
                lineEntity.getDate(),
                lineEntity.getLength(),
                doPointFromList(objectMapper.readValue(lineEntity.getCoordinates(), CoordinatesOfLine.class)));
    }

    private List<Point> doPointFromList(CoordinatesOfLine coordinates) {
        return coordinates.getCoordinates().stream()
                .filter(doubles -> doubles.size() == 2)
                .map(doubles -> new Point(doubles.get(0), doubles.get(1)))
                .collect(Collectors.toUnmodifiableList());
    }
}