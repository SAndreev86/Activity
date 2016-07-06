package ru.simbirsoft.intensiv.workWithDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by user on 06.07.2016.
 */
public class ConnectDB {

    private Connection c;
    private static ConnectDB con = new ConnectDB();
    private ConnectDB(){
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:base.db");
            c.setAutoCommit(false);
        }
        catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static ConnectDB getCon() {
        return con;
    }

    public Connection getC() {
        return c;
    }
}
