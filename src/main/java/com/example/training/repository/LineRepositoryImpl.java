package com.example.training.repository;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static com.example.training.jooq.tables.Line.LINE;
import static com.example.training.repository.utils.GeometryConverter.*;
import static org.jooq.impl.DSL.field;

@Repository
@RequiredArgsConstructor
public class LineRepositoryImpl implements LineRepository {

    private final DSLContext dsl;

    @Override
    public int save(String points) {
        String geometry = "ST_GeomFromText('LINESTRING(" + points + ")', 4326)";
        LocalDateTime now = LocalDateTime.now();

        return Objects.requireNonNull(dsl.insertInto(LINE, LINE.DATE, LINE.LENGTH, LINE.GEOM)
                .values( DATE_FIELD.apply(now), LENGTH_FIELD.apply(geometry), GEOMETRY_FIELD.apply(geometry))
                .returningResult(LINE.ID).fetchOne()).get(LINE.ID);
    }

    @Override
    public int delete(int id) {
        return dsl.delete(LINE)
                .where(LINE.ID.eq(id))
                .execute();
    }

    @Override
    public List<String> findAllGeom() {

        return dsl.select(field("st_astext(" + LINE.GEOM + ")"))
                .from(LINE)
                .fetchInto(String.class);
    }

    @Override
    public List<Integer> findAllLength() {

        return dsl.select(LINE.LENGTH)
                .from(LINE)
                .fetchInto(Integer.class);
    }
}