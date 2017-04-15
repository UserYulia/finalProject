package by.galkina.game.ajax.command.impl;

import by.galkina.game.ajax.command.Command;
import by.galkina.game.ajax.logic.SetBanLogic;
import by.galkina.game.exception.LogicException;
import by.galkina.game.exception.TechnicalException;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class SetBanCommand implements Command {
    private static final Logger LOG = Logger.getLogger(SetBanCommand.class);
    private static final String LANG = "lang";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response, String requestData) throws IOException {
        LOG.info("Edit ban command");
        HttpSession session = request.getSession(true);
        String lang = (String) session.getAttribute(LANG);
        try {
            SetBanLogic.set(requestData, lang);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (TechnicalException e) {
            LOG.error("Something has gone wrong.", e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (LogicException e) {
            LOG.error("LogicException", e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
