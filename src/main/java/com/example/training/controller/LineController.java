package com.example.training.controller;

import com.example.training.model.Line;
import com.example.training.model.utils.Point;
import com.example.training.service.LineServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<List<Line>> getAll() throws JsonProcessingException {
        return ResponseEntity.ok(service.getAll());
    }
}


