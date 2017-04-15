package by.galkina.game.logic;

import by.galkina.game.dao.impl.UserDao;
import by.galkina.game.entity.User;
import by.galkina.game.exception.DAOException;
import by.galkina.game.exception.LogicException;
import by.galkina.game.exception.TechnicalException;
import by.galkina.game.manager.MessageManager;
import org.apache.log4j.Logger;

import java.util.regex.Pattern;

public class RegistrationLogic {
    public static final Logger LOG = Logger.getLogger(RegistrationLogic.class);

    private static final String PARAM_EMAIL_VALIDATE =
            "^([a-z0-9_-]+\\.)*[a-z0-9_-]+@[a-z0-9_-]+(\\.[a-z0-9_-]+)*\\.[a-z]{2,6}$";
    private static final String PARAM_PASSWORD_validate = "^[а-яА-ЯёЁa-zA-Z0-9-_\\.]{4,19}$";

    public static void registrate(User user, String password,String passwordAgain, String locale) throws LogicException, TechnicalException {
        try {
            validate(user, password, passwordAgain, locale);
            if (checkEmail(user.getEmail())) {
                new UserDao().add(user);
                LOG.info("User was added");
            } else {
                throw new LogicException(MessageManager.getManagerByLocale(locale).getProperty(
                        MessageManager.REGISTRATION_WAS_INTERRUPT_MESSAGE));
            }
        } catch (DAOException e) {
            throw new TechnicalException(e);
        }
    }
    private static void validate(User user, String password, String passwordAgain, String locale) throws LogicException {
        validatePassword(password, passwordAgain, locale);
        validateField(PARAM_EMAIL_VALIDATE, MessageManager.EMAIL_IS_NOT_VALID, user.getEmail(), locale);
    }

    private static void validatePassword(String password, String passwordAgain, String locale) throws LogicException {
        if (!password.equals(passwordAgain) || !Pattern.matches(PARAM_PASSWORD_validate, password)) {
            throw new LogicException(MessageManager.getManagerByLocale(locale).getProperty(
                    MessageManager.PASSWORD_IS_NOT_VALID));
        }
    }
    private static void validateField(String pattern, String errorMessage, String field, String locale) throws LogicException {
        if (!Pattern.matches(pattern, field)) {
            throw new LogicException(MessageManager.getManagerByLocale(locale).getProperty(errorMessage));
        }
    }

    private static boolean checkEmail(String email) throws DAOException {
        return new UserDao().findByEmail(email)==null;
    }
}
