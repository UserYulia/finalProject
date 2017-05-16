package by.galkina.game.command.impl;

import by.galkina.game.command.Command;
import by.galkina.game.entity.User;
import by.galkina.game.exception.TechnicalException;
import by.galkina.game.logic.SendMessageLogic;
import by.galkina.game.manager.ConfigurationManager;
import by.galkina.game.manager.MessageManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SendMessageCommand implements Command {
    private static final String USER = "user";
    private static final String TEXT = "text";
    private static final String ERROR_MESSAGE = "errorMessage";

    @Override
    public String execute(HttpServletRequest request) {
        String page;
        HttpSession session = request.getSession(true);
        User user = (User) session.getAttribute(USER);
        String text = request.getParameter(TEXT);
        try {
            new SendMessageLogic().send(text, user.getEmail());
            page= ConfigurationManager.getInstance().getProperty(ConfigurationManager.USER_OFFICE_PAGE_PATH);
        } catch (TechnicalException e) {
            request.setAttribute(ERROR_MESSAGE, MessageManager.MESSAGE_WASNT_SENT);
            page= ConfigurationManager.getInstance().getProperty(ConfigurationManager.USER_OFFICE_PAGE_PATH);
        }
        return page;
    }

}
