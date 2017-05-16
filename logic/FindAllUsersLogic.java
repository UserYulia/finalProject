package by.galkina.game.logic;

import by.galkina.game.dao.impl.UserDao;
import by.galkina.game.entity.User;
import by.galkina.game.exception.DAOException;
import by.galkina.game.exception.TechnicalException;

import java.util.List;

public class FindAllUsersLogic {
    private final static String USERS = "users";
    public static List<User> find() throws TechnicalException {
        List<User> users = null;
        try {
            users = new UserDao().findAll();
        } catch (DAOException e) {
            throw new TechnicalException(e);
        }
        return users;
    }
}
