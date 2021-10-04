package Helpers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.sql.SQLException;

public class DBConnection {
    private static final String dbName="client_schedule";
    private static final String dbURL="jdbc:mysql://localhost:3306"+dbName;
    private static final String dbUser="sqlUser";
    private static final String dbPass="Passw0rd!";

    static Connection conn;

    public static void openConnection() throws ClassNotFoundException, SQLException, Exception{
        conn=(Connection) DriverManager.getConnection(dbURL, dbUser, dbPass);
    }

    public static void closeConnection() throws ClassNotFoundException, SQLException, Exception{
        conn.close();
    }
}
