package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String sqlQuery = "CREATE TABLE IF NOT EXISTS USERS (" +
                "id BIGINT KEY NOT NULL AUTO_INCREMENT, " +
                "name VARCHAR(50), " +
                "lastName VARCHAR(50), " +
                "age TINYINT);";
        try (Statement stmt = Util.getConnection().createStatement()) {
            stmt.execute(sqlQuery);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Статус ошибки - " + e.getSQLState());
        }
    }

    public void dropUsersTable() {
        String sqlQuery = "DROP TABLE IF EXISTS USERS;";
        try (Statement stmt = Util.getConnection().createStatement()) {
            stmt.execute(sqlQuery);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Статус ошибки - " + e.getSQLState());
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sqlQuery = "INSERT INTO USERS (name, lastName, age) VALUES (?, ?, ?);";
        try (PreparedStatement preparedStatement = Util.getConnection().prepareStatement(sqlQuery)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setInt(3, age);
            preparedStatement.execute();
            System.out.printf("User с именем – %s добавлен в базу данных%n", name);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Статус ошибки - " + e.getSQLState());
        }
    }

    public void removeUserById(long id) {
        String sqlQuery = "DELETE FROM USERS WHERE id=?;";
        try (PreparedStatement preparedStatement = Util.getConnection().prepareStatement(sqlQuery)) {
            preparedStatement.setLong(1, id);
            preparedStatement.execute(sqlQuery);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Статус ошибки - " + e.getSQLState());
        }
    }

    public List<User> getAllUsers() {
        String sqlQuery = "SELECT * FROM USERS";
        List<User> result = new ArrayList<>();
        try (Statement stmt = Util.getConnection().createStatement()) {
            stmt.execute(sqlQuery);
            ResultSet resultSet = stmt.getResultSet();
            while (resultSet.next()) {
                User user = new User(
                        resultSet.getString("name"),
                        resultSet.getString("lastName"),
                        resultSet.getByte("age")
                );
                user.setId(resultSet.getLong("id"));
                result.add(user);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Статус ошибки - " + e.getSQLState());
        }
        return result;
    }

    public void cleanUsersTable() {
        String sqlQuery = "DELETE FROM USERS;";
        try (Statement stmt = Util.getConnection().createStatement()) {
            stmt.execute(sqlQuery);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Статус ошибки - " + e.getSQLState());
        }
    }
}
