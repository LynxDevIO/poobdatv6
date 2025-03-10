package dev.phil.poobdatv6.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private final String URL = "jdbc:postgresql://localhost:5432/gerenciador";
    private final String USER = "postgres";
    private final String PASSWORD = "postgres";
    private static volatile DBConnection instance;
    private static Connection connection;

    private DBConnection() throws SQLException {
        connection = DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static DBConnection getInstance() throws SQLException {
        DBConnection i = instance;
        if (i != null) {
            return i;
        }

        synchronized (DBConnection.class) {
            if (instance == null) {
                instance = new DBConnection();
            }
            return instance;
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
