package jm.task.core.jdbc.util;

import java.sql.*;

public class Util {
    public static Connection getConnection() {
        String login = System.getenv("MYSQL_LOGIN") == null ?
                "LOGIN123" : System.getenv("MYSQL_LOGIN");
        String password = System.getenv("MYSQL_PASSWORD") == null ?
                "PASSWORD123" : System.getenv("MYSQL_PASSWORD");
        String database = System.getenv("MYSQL_DATABASE") == null ?
                "KATA_DB" : System.getenv("MYSQL_DATABASE");
        String url = System.getenv("MYSQL_DATABASE") == null ?
                "localhost" : System.getenv("MYSQL_DATABASE");
        String driverName = System.getenv("SQL_DRIVER") == null ?
                "mysql" : System.getenv("SQL_DRIVER");
        int port = System.getenv("MYSQL_DATABASE") == null ?
                3306 : Integer.parseInt(System.getenv("MYSQL_DATABASE"));
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(String.format("jdbc:%s://%s:%s/%s", driverName, url, port, database), login, password);
        } catch (SQLException e) {
            System.out.println(e.getSQLState());
            System.out.println(e.getMessage());
            System.out.println("Приложение будет закрыто");
            System.exit(1);
        }
        return connection;
    }
}