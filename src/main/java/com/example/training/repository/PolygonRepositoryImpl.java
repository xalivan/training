package com.example.training.repository;

import com.example.training.model.PolygonEntity;
import lombok.RequiredArgsConstructor;
import org.jooq.CommonTableExpression;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Record1;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static com.example.training.jooq.tables.Polygon.POLYGON;
import static com.example.training.repository.utils.PostGisUtils.*;
import static org.jooq.impl.DSL.*;

@Repository
@RequiredArgsConstructor
public class PolygonRepositoryImpl implements PolygonRepository {
    private final DSLContext dsl;

    @Override
    public int save(String points) {
        CommonTableExpression<Record1<String>> geometry_table = name("geometry_table").fields("geometry_column")
                .as(select(val(ST_MAKE_POLYGON.apply(ST_GEOM_FROM_TEXT.apply(points)))));
        String geometry = Objects.requireNonNull(select(geometry_table.field("geometry_column"))
                        .from(geometry_table)
                        .fetchAny())
                .into(String.class);
        return Objects.requireNonNull(dsl.insertInto(POLYGON, POLYGON.SQUARE, POLYGON.GEOMETRY)
                        .values(ST_AREA.apply(geometry), field(geometry, Object.class))
                        .returningResult(POLYGON.ID)
                        .fetchOne())
                .into(int.class);
    }

    @Override
    public int delete(int id) {
        return dsl.delete(POLYGON)
                .where(POLYGON.ID.eq(id))
                .execute();
    }

    @Override
    public List<PolygonEntity> findAll() {
        return dsl.select(POLYGON.ID, POLYGON.SQUARE, ST_AS_GEO_JSON.apply(POLYGON.GEOMETRY))
                .from(POLYGON).fetchInto(PolygonEntity.class);
    }

    @Override
    public int buffer(int id, double distance) {
        CommonTableExpression<Record1<String>> geometry_table = name("geometry_table")
                .as((select(ST_BUFFER.apply(POLYGON.GEOMETRY, distance).as("buffered_geometry")))
                        .from(POLYGON)
                        .where(POLYGON.ID.eq(id)));

        return Objects.requireNonNull(
                dsl.with(geometry_table)
                        .update(POLYGON)
                        .set(POLYGON.SQUARE, ST_AREA.apply(String.valueOf(geometry_table.field("buffered_geometry"))))
                        .set(POLYGON.GEOMETRY, geometry_table.field("buffered_geometry"))
                        .from(geometry_table)
                        .where(POLYGON.ID.eq(id))
                        .returningResult(POLYGON.ID).fetchOne()).get(POLYGON.ID);
    }
}
