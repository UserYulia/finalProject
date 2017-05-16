package by.galkina.game.command.impl;

import by.galkina.game.command.Command;
import by.galkina.game.entity.User;
import by.galkina.game.exception.LogicException;
import by.galkina.game.exception.TechnicalException;
import by.galkina.game.logic.ChangePasswordLogic;
import by.galkina.game.logic.Encryptor;
import by.galkina.game.manager.ConfigurationManager;
import by.galkina.game.manager.MessageManager;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


public class ChangePasswordCommand implements Command {
    private static final Logger LOG = LogManager.getLogger(ChangePasswordCommand.class);
    private static final String PASSWORD = "password";
    private static final String PASSWORD_AGAIN = "passwordAgain";
    private static final String NEW_PASSWORD = "newPassword";
    private static final String USER = "user";
    private static final String PARAM_ERROR_MESSAGE = "errorMessage";
    private static final String PARAM_ACTION_MESSAGE = "actionMessage";
    private static final String LANG = "lang";

    @Override
    public String execute(HttpServletRequest request) {
        LOG.info("Change password command");
        String page;
        String newPassword;
        String password;
        String passwordAgain;
        HttpSession session = request.getSession(true);
        String lang = (String) session.getAttribute(LANG);
        try {
            password = (request.getParameter(PASSWORD)).trim();
            passwordAgain = (request.getParameter(PASSWORD_AGAIN)).trim();
            newPassword = (request.getParameter(NEW_PASSWORD)).trim();
            User user = (User)request.getSession().getAttribute(USER);
            ChangePasswordLogic.change(user, password, passwordAgain, newPassword, lang);
            user.setPassword(Encryptor.encipherPassword(newPassword));
            request.getSession().setAttribute(USER, user);
            request.setAttribute(PARAM_ACTION_MESSAGE, MessageManager.getManagerByLocale(lang).getProperty(
                    MessageManager.SUCCESS_CHANGE_PASSWORD));
            page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.USER_OFFICE_PAGE_PATH);
        } catch (TechnicalException e) {
            LOG.error("Something has gone wrong, redirect to error page.", e);
            request.setAttribute(PARAM_ERROR_MESSAGE, e);
            page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.ERROR_PAGE_PATH);
        } catch (LogicException e) {
            request.setAttribute(PARAM_ERROR_MESSAGE, e.getMessage());
            page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.USER_OFFICE_PAGE_PATH);
        }
        return page;
    }
}
