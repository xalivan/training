package com.example.training.repository.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.jooq.Field;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;

import java.util.function.BiFunction;
import java.util.function.Function;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PostGisUtils {
    private static final int SRID = 4326; //26918 --4326
    private static final String JOIN = ", 'join=mitre mitre_limit=20.0'";

    //line
    public static final Function<String, Field<Integer>> ST_LENGTH = geometry ->
            DSL.field("ST_length(" + geometry + ")", SQLDataType.INTEGER);

    public static final Function<String, String> ST_GEOM_FROM_TEXT = points ->
            "ST_SetSRID(ST_GeomFromText('LINESTRING(" + points + ")')," + SRID + ")";

    public static final Function<Field<Object>, Field<String>> ST_AS_GEO_JSON = geometry ->
            DSL.field("ST_AsGeoJSON(" + geometry + ")", String.class);

    //polygon
    public static final Function<String, Field<Double>> ST_AREA = polygon ->
            DSL.field("ST_Area(" + polygon + ")", SQLDataType.DOUBLE);

    public static final Function<String, String> ST_MAKE_POLYGON = geometryAsText ->
            "ST_MakePolygon( " + geometryAsText + ")::geometry";

    public static final BiFunction<Object, Double, Field<String>> ST_BUFFER = (geom, dist) ->
            DSL.field("ST_Buffer(" + geom + "::geography," + dist + JOIN + ")::geometry(Polygon," + SRID + ")", String.class);
}
