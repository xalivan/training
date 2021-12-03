package com.example.training.utils;

import org.jooq.DSLContext;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
    public static Connection connection;
    public static DSLContext dsl;

    public static void openingConnection() throws SQLException {
        connection = DriverManager.getConnection("jdbc:postgresql://localhost:5435/test", "admin", "1111");
        dsl = DSL.using(connection);
    }
}
