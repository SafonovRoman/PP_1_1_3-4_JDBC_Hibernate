package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;

public class Main {
    public static void main(String[] args) {
        UserDaoJDBCImpl sqlDAO = new UserDaoJDBCImpl();
        sqlDAO.createUsersTable();
        sqlDAO.saveUser("Роман", "Сафонов", (byte) 36);
        sqlDAO.saveUser("Виктор", "Александров", (byte) 20);
        sqlDAO.saveUser("Сергей", "Борзой", (byte) 36);
        sqlDAO.saveUser("Иван", "Дурак", (byte) 120);
        for (User user : sqlDAO.getAllUsers()) {
            System.out.println(user);
        }
        sqlDAO.cleanUsersTable();
        sqlDAO.dropUsersTable();
    }
}
