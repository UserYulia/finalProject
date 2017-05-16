package by.galkina.game.command.impl;

import by.galkina.game.command.Command;
import by.galkina.game.entity.Role;
import by.galkina.game.entity.User;
import by.galkina.game.exception.LogicException;
import by.galkina.game.exception.TechnicalException;
import by.galkina.game.logic.FindAllGamesLogic;
import by.galkina.game.logic.FindAllMessagesLogic;
import by.galkina.game.logic.FindAllUsersLogic;
import by.galkina.game.logic.LoginLogic;
import by.galkina.game.manager.ConfigurationManager;
import by.galkina.game.manager.MessageManager;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


public class LoginCommand implements Command {
    private static final Logger LOG = LogManager.getLogger(LoginCommand.class);
    private static final String PARAM_USERS = "users";
    private static final String PARAM_MESSAGES = "messages";
    private static final String PARAM_GAMES = "games";
    private static final String PARAM_USER = "user";
    private static final String PARAM_NAME_EMAIL = "email";
    private static final String PARAM_NAME_PASSWORD = "password";
    private static final String PARAM_LANG = "lang";
    private static final String PARAM_ERROR_MESSAGE = "errorMessage";

    public String execute(HttpServletRequest request) {
        LOG.info("login command");
        String page;
        User user;
        HttpSession session = request.getSession(true);
        String lang = (String) session.getAttribute(PARAM_LANG);
        String email = request.getParameter(PARAM_NAME_EMAIL).trim();
        String password = request.getParameter(PARAM_NAME_PASSWORD);

        try {
            if ((user = LoginLogic.checkEmail(email, password)) != null) {
                LOG.info("Add to session user.");
                session.setAttribute(PARAM_USER, user);
                if(user.getRole()== Role.ADMIN){
                    session.setAttribute(PARAM_USERS, FindAllUsersLogic.find());
                    session.setAttribute(PARAM_MESSAGES, FindAllMessagesLogic.find());
                    page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.ADMIN_OFFICE_PAGE_PATH);
                }
                else {
                    session.setAttribute(PARAM_GAMES, FindAllGamesLogic.find(user));
                    page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.USER_OFFICE_PAGE_PATH);
                }
            } else {
                request.setAttribute(PARAM_ERROR_MESSAGE, MessageManager.getManagerByLocale(lang).getProperty(
                        MessageManager.LOGIN_ERROR_MESSAGE));
                page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.SIGN_IN_PAGE_PATH);
            }
        } catch (TechnicalException e) {
            LOG.error("Something has gone wrong, redirect to error page.", e);
            request.setAttribute(PARAM_ERROR_MESSAGE, MessageManager.getManagerByLocale(lang).getProperty(
                    MessageManager.LOGIC_EXCEPTION_ERROR_MESSAGE));
            page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.ERROR_PAGE_PATH);
        } catch (LogicException e) {
            LOG.error("LogicException", e);
            request.setAttribute(PARAM_ERROR_MESSAGE, MessageManager.getManagerByLocale(lang).getProperty(
                    MessageManager.LOGIN_BANNED_MESSAGE));
            page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.MAIN_PAGE_PATH);
        }

        return page;
    }
}