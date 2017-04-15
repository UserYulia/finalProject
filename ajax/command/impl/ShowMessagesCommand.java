package by.galkina.game.ajax.command.impl;

import by.galkina.game.ajax.command.Command;
import by.galkina.game.ajax.logic.FindAllMessagesLogic;
import by.galkina.game.exception.TechnicalException;
import by.galkina.game.manager.MessageManager;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;


public class ShowMessagesCommand implements Command {
    private static final Logger LOG = Logger.getLogger(FindAllUsersCommand.class);
    private static final String TEXT = "text";
    private static final String LANG = "lang";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response, String requestData) throws IOException {
        LOG.info("Show messages command");
        PrintWriter out = response.getWriter();
        JSONObject object = new JSONObject();
        HttpSession session = request.getSession(true);
        String lang = (String) session.getAttribute(LANG);
        try {
            String messages = FindAllMessagesLogic.find();
            out.println(messages);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (TechnicalException e) {
            LOG.error("Something has gone wrong.", e);
            object.put(TEXT, MessageManager.getManagerByLocale(lang).getProperty(""));
            out.println(object);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }finally {
            out.flush();
            out.close();
        }
    }
}