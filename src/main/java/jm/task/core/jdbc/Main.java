package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Роман", "Сафонов", (byte) 36);
        userService.saveUser("Виктор", "Александров", (byte) 20);
        userService.saveUser("Сергей", "Борзой", (byte) 36);
        userService.saveUser("Иван", "Дурак", (byte) 120);
        for (User user : userService.getAllUsers()) {
            System.out.println(user);
        }
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
