package com.example.training.repository;

import com.example.training.model.Polygon;
import com.example.training.model.PolygonEntity;
import lombok.RequiredArgsConstructor;
import org.jooq.CommonTableExpression;
import org.jooq.DSLContext;
import org.jooq.Record1;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

import static com.example.training.jooq.tables.Polygon.POLYGON;
import static com.example.training.repository.utils.PostGisUtils.*;
import static org.jooq.impl.DSL.*;

@Repository
@RequiredArgsConstructor
public class PolygonRepositoryImpl implements PolygonRepository {
    private static final String POLYGON_TABLE = "polygon_table";
    private static final String BUFFERED_GEOMETRY = "buffered_geometry";
    private static final String POLYGON_VALUE = "polygon_value";
    private final DSLContext dsl;

    @Override
    public int save(Polygon points) {
        CommonTableExpression<Record1<String>> polygonTable = name(POLYGON_TABLE)
                .as(select(ST_GEOM.apply(points.toWKTString()).as(POLYGON_VALUE)));
        return Objects.requireNonNull(dsl.with(polygonTable)
                        .insertInto(POLYGON, POLYGON.SQUARE, POLYGON.GEOMETRY)
                        .select(select(ST_AREA.apply(POLYGON_VALUE), field(POLYGON_VALUE)).from(polygonTable))
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
    public PolygonEntity buffer(int id, double distance) {
        CommonTableExpression<Record1<String>> polygonTable = name(POLYGON_TABLE)
                .as((select(ST_BUFFER.apply(POLYGON.GEOMETRY, distance).as(BUFFERED_GEOMETRY)))
                        .from(POLYGON)
                        .where(POLYGON.ID.eq(id)));
        return Objects.requireNonNull(dsl.with(polygonTable)
                        .update(POLYGON)
                        .set(POLYGON.SQUARE, ST_AREA.apply(BUFFERED_GEOMETRY))
                        .set(POLYGON.GEOMETRY, BUFFERED_GEOMETRY)
                        .from(polygonTable)
                        .where(POLYGON.ID.eq(id))
                        .returningResult(POLYGON.ID, POLYGON.SQUARE, ST_AS_GEO_JSON.apply(POLYGON.GEOMETRY))
                        .fetchOne())
                .into(PolygonEntity.class);
    }
}
