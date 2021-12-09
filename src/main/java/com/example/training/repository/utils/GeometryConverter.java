package com.example.training.repository.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.jooq.Field;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;

import java.time.LocalDate;
import java.util.function.Function;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GeometryConverter {
    public static final Function<String, Field<Integer>> LENGTH_FIELD = points ->
            DSL.field("ST_length(ST_GeomFromText('LINESTRING(" + points + ")', 4326))", SQLDataType.INTEGER);

    public static final Function<String, Field<Object>> GEOMETRY_FIELD = points ->
            DSL.field("ST_GeomFromText('LINESTRING(" + points + ")', 4326)");

    public static final Function<LocalDate, Field<LocalDate>> DATE_FIELD = localDate ->
            DSL.field("{0}", SQLDataType.LOCALDATE, localDate);
}
