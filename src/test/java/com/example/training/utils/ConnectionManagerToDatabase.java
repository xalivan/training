package com.example.training.utils;

import org.jooq.DSLContext;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManagerToDatabase {


    public static DSLContext openingConnection()  {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5435/test", "admin", "1111");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return DSL.using(connection);

    }
}
