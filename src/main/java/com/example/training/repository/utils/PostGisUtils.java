package com.example.training.repository.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.jooq.Field;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;

import java.util.function.Function;

import static com.example.training.repository.PolygonRepositoryImpl.BUFFERED_GEOMETRY;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PostGisUtils {
        private static final int SRID = 4326;

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
            "ST_MakePolygon( " + geometryAsText + ")";

    public static final Function<Double, Object> ST_BUFFER = distance ->
            DSL.field("ST_Buffer(" + BUFFERED_GEOMETRY + "," + distance + ")", String.class) ;

}
