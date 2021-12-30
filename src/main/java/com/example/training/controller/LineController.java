package com.example.training.controller;

import com.example.training.model.Line;
import com.example.training.model.Polygon;
import com.example.training.model.utils.Point;
import com.example.training.service.LineServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@RestController
@RequestMapping("line")
@RequiredArgsConstructor
public class LineController {
    private final LineServiceImpl service;

    @PutMapping
    public ResponseEntity<Integer> save(@RequestBody List<Point> points) {
        return ResponseEntity.ok(service.save(points));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        return service.delete(id) ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

    @GetMapping
    public ResponseEntity<List<Line>> getAll()  {
        try {
            return ResponseEntity.ok(service.getAll());
        } catch (JsonProcessingException e) {
            log.error("Error parsing to LineCoordinates {0} ", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}


