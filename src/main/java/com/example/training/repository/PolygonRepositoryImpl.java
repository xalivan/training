package com.example.training.repository;

import com.example.training.model.PolygonEntity;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

import static com.example.training.jooq.tables.Polygon.POLYGON;
import static com.example.training.repository.utils.PostGisUtils.*;
import static org.jooq.impl.DSL.field;

@Repository
@RequiredArgsConstructor
public class PolygonRepositoryImpl implements PolygonRepository {
    private final DSLContext dsl;

    @Override
    public int save(String points) {
        return Objects.requireNonNull(dsl.insertInto(POLYGON, POLYGON.SQUARE, POLYGON.GEOMETRY)
                .values(ST_AREA.apply(ST_MAKE_POLYGON.apply(ST_GEOM_FROM_TEXT.apply(points))),
                        field(ST_GEOM_FROM_TEXT.apply(points)))
                .returningResult(POLYGON.ID).fetchOne()).get(POLYGON.ID);
    }

    @Override
    public int delete(int id) {
        return dsl.delete(POLYGON)
                .where(POLYGON.ID.eq(id))
                .execute();
    }

    @Override
    public List<PolygonEntity> findAll() {
        return dsl.select(POLYGON.ID, POLYGON.SQUARE, ST_AS_GEO_JSON.apply(String.valueOf(POLYGON.GEOMETRY)))
                .from(POLYGON).fetchInto(PolygonEntity.class);
    }

    @Override
    public int buffer(int id, double distance) {
        return Objects.requireNonNull(dsl.update(POLYGON)
                .set(POLYGON.SQUARE, ST_AREA.apply(ST_BUFFER.apply(distance)))
                .set(POLYGON.GEOMETRY, (Object) field(ST_BUFFER.apply(distance)))
                .where(POLYGON.ID.eq(id))
                .returningResult(POLYGON.ID).fetchOne()).get(POLYGON.ID);
    }
}
