package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.sql.*;
import java.util.Properties;

public class Util {
    private static SessionFactory sessionFactory;
    private static final String login = System.getenv("MYSQL_LOGIN") == null ?
            "LOGIN123" : System.getenv("MYSQL_LOGIN");
    private static final String password = System.getenv("MYSQL_PASSWORD") == null ?
            "PASSWORD123" : System.getenv("MYSQL_PASSWORD");
    private static final String database = System.getenv("MYSQL_DATABASE") == null ?
            "KATA_DB" : System.getenv("MYSQL_DATABASE");
    private static final String url = System.getenv("MYSQL_DATABASE") == null ?
            "localhost" : System.getenv("MYSQL_DATABASE");
    private static final String driverName = System.getenv("SQL_DRIVER") == null ?
            "mysql" : System.getenv("SQL_DRIVER");
    private static final int port = System.getenv("MYSQL_DATABASE") == null ?
            3306 : Integer.parseInt(System.getenv("MYSQL_DATABASE"));
    private static Connection connection = null;

    public static Connection getConnection() {
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

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            Configuration configuration = new Configuration();
            Properties settings = new Properties();
            settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
            settings.put(Environment.URL, String.format("jdbc:%s://%s:%s/%s?useSSL=false", driverName, url, port, database));
            settings.put(Environment.USER, login);
            settings.put(Environment.PASS, password);
            settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");
            settings.put(Environment.SHOW_SQL, "true");
            settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
            configuration.setProperties(settings);
            configuration.addAnnotatedClass(User.class);

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();

            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        }
        return sessionFactory;
    }
}

