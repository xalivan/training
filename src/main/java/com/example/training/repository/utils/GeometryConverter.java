package com.example.training.repository.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.jooq.Field;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;

import java.util.function.Function;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GeometryConverter {
    public static final Function<String, Field<Integer>> LENGTH_FIELD = geometry ->
            DSL.field("ST_length(" + geometry + ")", SQLDataType.INTEGER);

    public static final Function<String, Field<Object>> GEOMETRY_FIELD = DSL::field;

    public static final Function<String, String> GEOMETRY = points ->
            "ST_GeomFromText('LINESTRING(" + points + ")', 4326)";

    public static final Function<String, Field<String>> GEOMETRY_SELECT_FIELD_AS_GEO_JSON = geometry ->
            DSL.field("ST_AsGeoJSON(" + geometry + ")", String.class);
}
