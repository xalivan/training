package com.example.training.controller;

import com.example.training.model.Polygon;
import com.example.training.model.utils.Point;
import com.example.training.service.PolygonServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("polygon")
@RequiredArgsConstructor
public class PolygonController {
    private final PolygonServiceImpl service;

    @PutMapping
    public ResponseEntity<Integer> save(@RequestBody List<Point> points) {
        return ResponseEntity.ok(service.save(points));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        return service.delete(id) ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

    @GetMapping
    public ResponseEntity<List<Polygon>> getAll() {
        try {
            return ResponseEntity.ok(service.getAll());
        } catch (JsonProcessingException e) {
            log.error("Error parsing to PolygonCoordinates {0} ", e);
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("{id}/{distance}")
    public ResponseEntity<Integer> buffer(@PathVariable int id, @PathVariable double distance) {
        return ResponseEntity.ok(service.buffer(id, distance));
    }
}
