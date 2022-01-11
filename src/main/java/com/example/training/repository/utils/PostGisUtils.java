package com.example.training.repository.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.jooq.Field;
import org.jooq.impl.SQLDataType;

import java.util.function.BiFunction;
import java.util.function.Function;

import static org.jooq.impl.DSL.field;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PostGisUtils {
    private static final String BUFFER_JOIN = ", 'join=mitre'";

    public static final Function<String, Field<Integer>> ST_LENGTH = geometry ->
            field("ST_length(" + geometry + ")", SQLDataType.INTEGER);

    public static final Function<String, String> ST_GEOM_FROM_TEXT = points ->
            "ST_GeomFromText('LINESTRING(" + points + ")')";

    public static final Function<Field<Object>, Field<String>> ST_AS_GEO_JSON = geometry ->
            field("ST_AsGeoJSON(" + geometry + ")", String.class);

    public static final Function<Object, Field<Double>> ST_AREA = polygon ->
            field("ST_Area(" + polygon + ")", Double.class);

    public static final Function<String, Field<String>> ST_MAKE_POLYGON = geometryAsText ->
            field("ST_MakePolygon( " + geometryAsText + ")", String.class);

    public static final BiFunction<Object, Double, Field<String>> ST_BUFFER = (geom, dist) ->
            field("ST_Buffer(" + geom + "::geography," + dist + BUFFER_JOIN + ")::geometry", String.class);
}
