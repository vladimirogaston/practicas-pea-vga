package ar.vga.com.mapper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class H2 {
    public static Connection makeConnection() {
        Connection conn = null;
        try {
            Class.forName("org.h2.Driver");
            conn = DriverManager.getConnection("jdbc:h2:~/test" + ";" + "INIT=RUNSCRIPT FROM 'classpath:script.sql'", "sa", "");
            System.out.println("Connection open");
        } catch (SQLException e) {
            System.out.println("Connection fails: " + e.getMessage());
        } catch (ClassNotFoundException e1) {
            System.out.println("Error driver registry: " + e1.getMessage());
        }
        return conn;
    }
}