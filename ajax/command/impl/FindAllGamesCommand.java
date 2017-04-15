package by.galkina.game.ajax.command.impl;

import by.galkina.game.ajax.command.Command;
import by.galkina.game.ajax.logic.FindAllGamesLogic;
import by.galkina.game.entity.User;
import by.galkina.game.exception.TechnicalException;
import by.galkina.game.manager.MessageManager;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public class FindAllGamesCommand implements Command {
    private static final Logger LOG = Logger.getLogger(FindAllGamesCommand.class);

    private static final String TEXT = "text";
    private static final String LANG = "lang";
    private static final String USER = "user";
    @Override
        public void execute(HttpServletRequest request, HttpServletResponse response, String requestData) throws IOException {
            LOG.info("Find all games command");
            PrintWriter out = response.getWriter();
            JSONObject object = new JSONObject();
            HttpSession session = request.getSession(true);
            String lang = (String) session.getAttribute(LANG);
            try {
                String tariffs = FindAllGamesLogic.find((User)session.getAttribute(USER));
                out.println(tariffs);
                response.setStatus(HttpServletResponse.SC_OK);
            } catch (TechnicalException e) {
                LOG.error("Something has gone wrong.", e);
                object.put(TEXT, MessageManager.getManagerByLocale(lang).getProperty(""));
                out.println(object);
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            } finally {
                out.flush();
                out.close();
            }
    }
}
