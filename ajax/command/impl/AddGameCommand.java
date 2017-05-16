package by.galkina.game.ajax.command.impl;

import by.galkina.game.ajax.command.Command;
import by.galkina.game.ajax.logic.AddGameLogic;
import by.galkina.game.entity.User;
import by.galkina.game.exception.LogicException;
import by.galkina.game.exception.TechnicalException;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AddGameCommand implements Command {
    private static final Logger LOG = Logger.getLogger(SetBanCommand.class);
    private static final String USER = "user";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response, String requestData) throws IOException {
        LOG.info("Add game command");
        HttpSession session = request.getSession(true);
        User user = (User)session.getAttribute(USER);
        try {
            AddGameLogic.add(user, requestData);
            response.setStatus(HttpServletResponse.SC_OK);
        }catch (LogicException | TechnicalException e) {
            LOG.error("LogicException", e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
