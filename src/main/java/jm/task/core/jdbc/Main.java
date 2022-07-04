package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.HibernateUtil;
import org.hibernate.Session;

public class Main {
    public static void main(String[] args) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.close();
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Роман", "Сафонов", (byte) 36);
        userService.saveUser("Виктор", "Александров", (byte) 20);
        userService.saveUser("Сергей", "Борзой", (byte) 36);
        userService.saveUser("Иван", "Дурак", (byte) 120);
        userService.removeUserById(13);
        for (User user : userService.getAllUsers()) {
            System.out.println(user);
        }
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
