package com.example.training.repository.utils;

import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Component;

@Component
public class GeometryDatabaseConnectionManager {
    private static final String USER_NAME = "admin";
    private static final String USER_PASSWORD = "1111";
    private static final String CONNECTION_STR = "jdbc:postgresql://localhost:5432/gis";
    public static final DSLContext DSL_CONTEXT = DSL.using(CONNECTION_STR, USER_NAME, USER_PASSWORD);

    private GeometryDatabaseConnectionManager() {
    }
}
