package com.example.training.service;

import com.example.training.model.Line;
import com.example.training.model.Point;
import com.example.training.repository.LineRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Service
@RequiredArgsConstructor
public class LineServiceImpl implements LineService {
    private final LineRepository repository;
    private final DSLContext dsl;

    @Override
    public List<Line> getAll() {
        List<String> allGeom = repository.findAllGeom();
        List<Integer> allLength = repository.findAllLength();
        List<List<Point>> pointList = new ArrayList<>();

        for (int i = 0; i < allGeom.size(); i++) {
            List<Double> doubleList = new ArrayList<>();
            String s = allGeom.get(i);
            Pattern p = Pattern.compile("\\d+");
            Matcher m = p.matcher(s);
            while (m.find()) {
                doubleList.add(Double.parseDouble(m.group()));
            }
            List<Point> points = IntStream.range(1, doubleList.size())
                    .filter(x -> x % 2 == 1)
                    .mapToObj((j) -> new Point(doubleList.get(j-1), doubleList.get(j)))
                    .collect(Collectors.toList());
            pointList.add(points);

        }
        List<Line>lineList=new ArrayList<>();
        for (int i = 0; i < allLength.size(); i++) {
            Line line=new Line(allLength.get(i), pointList.get(i));
            lineList.add(line);
            System.out.println(line);
        }
        System.out.println(lineList);

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
}