package project;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract  class DatabaseConn {
    private static Connection conn = null;// unify all connection

    public static Connection getConnection() {

        try {
            Class.forName("org.sqlite.JDBC");
            if(conn == null || conn.isClosed()) // dont make connection if there's already one
                conn = DriverManager.getConnection("jdbc:sqlite::resource:MovieRental.db");
            // Uncomment for testing database connection
//			Alert.Success("Database connected!");
            return conn;
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            return null;
        }

    }

    //check if all connections are closed
    public static boolean isAllConnectionClosed() {
        boolean result = false;
        try {
            result = conn.isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    //if all connections are not closed, close them
    public static void closeAllConnection() {
        try	{
            conn.close();
            conn = null;
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
