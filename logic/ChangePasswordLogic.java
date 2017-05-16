package by.galkina.game.logic;

import by.galkina.game.dao.impl.UserDao;
import by.galkina.game.entity.User;
import by.galkina.game.exception.DAOException;
import by.galkina.game.exception.LogicException;
import by.galkina.game.exception.TechnicalException;
import by.galkina.game.manager.MessageManager;

import java.util.regex.Pattern;


public class ChangePasswordLogic {
    private static final String PARAM_PASSWORD_VALIDATION = "^[а-яА-ЯёЁa-zA-Z0-9-_\\.]{4,19}$";


    public static void change(User user,String password, String passwordAgain, String newPassword, String locale) throws TechnicalException, LogicException {
        try {
            validateEqualsPassword(password, passwordAgain, locale);
            checkPassword(user, password, locale);
            validatePassword(newPassword, locale);
            new UserDao().changeUserPassword(user, Encryptor.encipherPassword(newPassword));
        } catch (DAOException e) {
            throw new TechnicalException(e);
        }
    }
    
    private static void checkPassword(User user, String password, String locale) throws LogicException {
        if (!user.getPassword().equals(Encryptor.encipherPassword(password))) {
            throw new LogicException(MessageManager.getManagerByLocale(locale).getProperty(
                    MessageManager.PASSWORDS_NOT_EQUALS));
        }
    }
    
    private static void validateEqualsPassword(String password, String passwordAgain, String locale) throws LogicException {
        if (!password.equals(passwordAgain) && Pattern.matches(PARAM_PASSWORD_VALIDATION, password)) {
            throw new LogicException(MessageManager.getManagerByLocale(locale).getProperty(
                    MessageManager.PASSWORDS_NOT_EQUALS));
        }
    }

    
    private static void validatePassword(String password, String locale) throws LogicException {
        if (!Pattern.matches(PARAM_PASSWORD_VALIDATION, password)) {
            throw new LogicException(MessageManager.getManagerByLocale(locale).getProperty(
                    MessageManager.PASSWORD_IS_NOT_VALID));
        }
    }
}
