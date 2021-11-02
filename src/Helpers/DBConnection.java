package Helpers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.sql.SQLException;

public class DBConnection {

   // private static final String sqlDriver = "com.mysql.jdbc.Driver"; //Not sure if this is used

    private static final String dbName="client_schedule";
    private static final String dbURL="jdbc:mysql://localhost:3306/"+dbName;
    private static final String dbUser="sqlUser";
    private static final String dbPass="Passw0rd!";

    public static Connection conn = null;

    public static Connection startConnection() throws Exception {
        conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
        return conn;
    }

    public static void closeConnection() throws Exception {
        conn.close();
    }


    // Get Connection
    public static Connection getConnection() throws Exception {
        return conn;
    }
}
