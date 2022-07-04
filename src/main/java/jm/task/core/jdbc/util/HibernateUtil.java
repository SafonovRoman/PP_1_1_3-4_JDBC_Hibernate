package jm.task.core.jdbc.util;

import java.util.Properties;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import jm.task.core.jdbc.model.User;

public class HibernateUtil {
    private static SessionFactory sessionFactory;
    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
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

                Configuration configuration = new Configuration();
                Properties settings = new Properties();
                settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
                settings.put(Environment.URL, String.format("jdbc:%s://%s:%s/%s?useSSL=false", driverName, url, port, database));
                settings.put(Environment.USER, login);
                settings.put(Environment.PASS, password);
                settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");
                settings.put(Environment.SHOW_SQL, "true");
                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
//                settings.put(Environment.HBM2DDL_AUTO, "update");
                configuration.setProperties(settings);
                configuration.addAnnotatedClass(User.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }
}