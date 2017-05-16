package by.galkina.game.command.impl;

import by.galkina.game.command.Command;
import by.galkina.game.entity.Gender;
import by.galkina.game.entity.Role;
import by.galkina.game.entity.User;
import by.galkina.game.exception.LogicException;
import by.galkina.game.exception.TechnicalException;
import by.galkina.game.logic.Encryptor;
import by.galkina.game.logic.LoginLogic;
import by.galkina.game.logic.RegistrationLogic;
import by.galkina.game.manager.ConfigurationManager;
import by.galkina.game.manager.MessageManager;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class RegistrationCommand implements Command {
    private static final Logger LOG = LogManager.getLogger(RegistrationCommand.class);

    private static final String USER = "user";
    private static final String USERNAME = "username";
    private static final String email = "email";
    private static final String PASSWORD = "password";
    private static final String PASSWORD_AGAIN = "passwordAgain";
    private static final String LANG = "lang";
    private static final String DATE = "date";
    private static final String GENDER = "gender";
    private static final String DATE_FORMAT = "dd-MM-yyyy";
    private static final String PARAM_ERROR_MESSAGE = "errorMessage";
    private static final String PARAM_ACTION_MESSAGE = "actionMessage";
    private static final String DEFAULT_PHOTO_PATH = "images\\photos\\default.jpg";


    public String execute(HttpServletRequest request) {
        LOG.info("Registration command");
        String page;
        HttpSession session = request.getSession();
        String password = request.getParameter(PASSWORD);
        String passwordAgain = request.getParameter(PASSWORD_AGAIN);
        String lang = (String) session.getAttribute(LANG);
        try {
            User user = create(request);
            RegistrationLogic.registrate(user, password, passwordAgain, lang);
            if(LoginLogic.checkEmail(user.getEmail(), user.getPassword())==null) {
                session.setAttribute(USER, user);
                page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.USER_OFFICE_PAGE_PATH);
                request.setAttribute(PARAM_ACTION_MESSAGE, MessageManager.getManagerByLocale(lang).getProperty(
                        MessageManager.REGISTRATION_WAS_SUCCESSFUL_MESSAGE));
            }
            else{
                request.setAttribute(PARAM_ERROR_MESSAGE,"Try another email");
                page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.REGISTRATION_PAGE_PATH);
            }
        } catch (TechnicalException e) {
            LOG.error("Something has gone wrong, redirect to error page.", e);
            request.setAttribute( PARAM_ERROR_MESSAGE, MessageManager.getManagerByLocale(lang).getProperty(
                    MessageManager.LOGIC_EXCEPTION_ERROR_MESSAGE));
            page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.ERROR_PAGE_PATH);
        } catch (LogicException e) {
            LOG.error("Something has gone wrong with registration.", e);
            request.setAttribute(PARAM_ERROR_MESSAGE, e.getMessage());
            page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.REGISTRATION_PAGE_PATH);
        } catch (ParseException e) {
            LOG.error("Date parse exception.", e);
            request.setAttribute( PARAM_ERROR_MESSAGE, MessageManager.getManagerByLocale(lang).getProperty(
                    MessageManager.LOGIC_EXCEPTION_ERROR_MESSAGE));
            page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.ERROR_PAGE_PATH);
        }
        return page;
    }
    private User create(HttpServletRequest request) throws ParseException {
        User user = new User();
        user.setEmail(request.getParameter(email));
        user.setPassword(Encryptor.encipherPassword(request.getParameter(PASSWORD)));
        user.setRole(Role.USER);
        user.setScore(new BigDecimal(1));
        user.setUsername(request.getParameter(USERNAME));
        user.setBan(false);
        user.setPhoto(DEFAULT_PHOTO_PATH);
        user.setRating(0.0);
        user.setGender(Gender.valueOf(request.getParameter(GENDER).toUpperCase()));
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern(DATE_FORMAT);
        Date birthday = format.parse(request.getParameter(DATE));
        user.setBirthday(birthday);
        LOG.info(user.toString());
        return user;
    }
}
