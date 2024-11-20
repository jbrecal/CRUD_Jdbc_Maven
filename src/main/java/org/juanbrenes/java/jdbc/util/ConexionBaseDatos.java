package org.juanbrenes.java.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBaseDatos {
    private static String url = "jdbc:mysql://localhost:3306/java_curso?serverTimezone=UTC";
    private static String user = "root";
    private static String pass = "sasa";
    private static Connection connection;

    public static Connection getConnection() throws SQLException {
        if (connection == null) {
            connection = DriverManager.getConnection(url, user, pass);
        }
        return connection;
    }
}
