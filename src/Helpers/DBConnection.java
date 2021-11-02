package Helpers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.sql.SQLException;

public class DBConnection {

    private static final String sqlDriver = "com.mysql.jdbc.Driver"; //Not sure if this is used

    private static final String dbName="client_schedule";
    private static final String dbURL="jdbc:mysql://localhost:3306/"+dbName;
    private static final String dbUser="sqlUser";
    private static final String dbPass="Passw0rd!";

    public static Connection conn = null;

    public static Connection startConnection() {
        try {
            Class.forName(sqlDriver);
            conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
    // Manage the connection
    public static void openConnection() throws ClassNotFoundException, SQLException, Exception{
        conn=(Connection) DriverManager.getConnection(dbURL, dbUser, dbPass);

    }

    public static void closeConnection() throws ClassNotFoundException, SQLException, Exception {
        conn.close();
    }


    // Get Connection
    public static Connection getConnection() {
        return conn;
    }
}
