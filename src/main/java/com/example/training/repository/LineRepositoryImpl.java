package com.example.training.repository;

import com.example.training.model.Line;
import com.example.training.repository.utils.GeometryDatabaseConnectionManager;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

import static com.example.training.jooq.tables.Line.LINE;
import static com.example.training.repository.utils.GeometryConverter.*;


@Repository
@RequiredArgsConstructor
public class LineRepositoryImpl implements LineRepository {

    private final DSLContext dsl = GeometryDatabaseConnectionManager.DSL_CONTEXT;

    @Override
    public int save(String points) {
        return dsl.insertInto(LINE, LINE.CREATION_DATE, LINE.LENGTH, LINE.GEOM)
                .values(DATE_FIELD.apply(LocalDate.now()), LENGTH_FIELD.apply(points), GEOMETRY_FIELD.apply(points))
                .execute();
    }

    @Override
    public int delete(int id) {
        return dsl.delete(LINE)
                .where(LINE.ID.eq(id))
                .execute();
    }

    @Override
    public List<Line> findAll() {
        return dsl.select(LINE.ID, LINE.CREATION_DATE, LINE.LENGTH, LINE.GEOM)
                .from(LINE)
                .fetchInto(Line.class);
    }
}