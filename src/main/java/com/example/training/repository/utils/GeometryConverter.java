package com.example.training.repository.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.jooq.Field;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;

import java.time.LocalDateTime;
import java.util.function.Function;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GeometryConverter {
    public static final Function<String, Field<Integer>> LENGTH_FIELD = geometry ->
            DSL.field("ST_length(" + geometry + ")", SQLDataType.INTEGER);

    public static final Function<String, Field<Object>> GEOMETRY_FIELD = DSL::field;

    public static final Function<LocalDateTime, Field<LocalDateTime>> DATE_FIELD = localDateTime ->
            DSL.field("{0}", SQLDataType.LOCALDATETIME, localDateTime);
}
