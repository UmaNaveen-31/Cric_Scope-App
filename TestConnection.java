package cricketer.main;

import java.sql.Connection;

import cricketer.util.DBConnection;

public class TestConnection {

    public static void main(String[] args) {

        try {

            Connection con =
                    DBConnection.getConnection();

            System.out.println(
                    "================================"
            );

            System.out.println(
                    "DATABASE CONNECTED SUCCESSFULLY"
            );

            System.out.println(
                    "================================"
            );

            con.close();

        } catch (Exception e) {

            System.out.println(
                    "DATABASE CONNECTION FAILED"
            );

            e.printStackTrace();
        }
    }
}
