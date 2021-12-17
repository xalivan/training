package com.example.training.repository;

import com.example.training.model.LineEntity;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;


import static com.example.training.jooq.tables.Line.LINE;
import static com.example.training.repository.utils.GeometryConverter.*;
import static org.jooq.impl.DSL.currentLocalDateTime;

@Repository
@RequiredArgsConstructor
public class LineRepositoryImpl implements LineRepository {

    private final DSLContext dsl;

    @Override
    public int save(String points) {

        return Objects.requireNonNull(dsl.insertInto(LINE, LINE.DATE, LINE.LENGTH, LINE.GEOMETRY)
                .values(currentLocalDateTime(),
                        ST_LENGTH.apply(ST_GEOM_FROM_TEXT.apply(points)),
                        GEOMETRY_FIELD.apply(ST_GEOM_FROM_TEXT.apply(points)))
                .returningResult(LINE.ID).fetchOne()).get(LINE.ID);
    }

    @Override
    public int delete(int id) {
        return dsl.delete(LINE)
                .where(LINE.ID.eq(id))
                .execute();
    }

    @Override
    public List<LineEntity> findAll() {
        return dsl.select(LINE.ID, LINE.DATE, LINE.LENGTH, ST_AS_GEO_JSON.apply(String.valueOf(LINE.GEOMETRY)))
                .from(LINE).fetchInto(LineEntity.class);
    }
}