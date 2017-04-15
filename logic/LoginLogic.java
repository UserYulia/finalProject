package by.galkina.game.logic;

import by.galkina.game.dao.impl.UserDao;
import by.galkina.game.entity.User;
import by.galkina.game.exception.DAOException;
import by.galkina.game.exception.LogicException;
import by.galkina.game.exception.TechnicalException;

public class LoginLogic {
    public static User checkEmail(String email, String password) throws TechnicalException, LogicException {
        User user = null;
        try {
            user = new UserDao().findByEmailAndPassword(email, Encryptor.encipherPassword(password));
        } catch (DAOException e) {
            throw new TechnicalException(e);
        }
        return user;
    }
}
