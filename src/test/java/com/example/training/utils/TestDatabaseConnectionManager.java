package com.example.training.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TestDatabaseConnectionManager {
    static String DB_URL = "jdbc:postgresql://localhost:5435/test";
    static String DB_USER = "admin";
    static String DB_PASSWORD = "1111";

    public static DSLContext getConnection() {
        return DSL.using(DB_URL, DB_USER, DB_PASSWORD);
    }
}
