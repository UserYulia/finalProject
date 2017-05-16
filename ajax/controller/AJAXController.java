package by.galkina.game.ajax.controller;


import by.galkina.game.ajax.command.Command;
import by.galkina.game.ajax.command.CommandDefiner;
import by.galkina.game.ajax.util.AJAXServletUtil;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/ajaxController")
public class AJAXController extends HttpServlet {
    private static final String CONTENT_TYPE = "application/json";
    private static final String UTF8 = "UTF-8";
    private static final Logger LOG = Logger.getLogger(AJAXController.class);

    @Override
    public void init() throws ServletException {
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.info("GET method");
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.info("POST method");
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType(CONTENT_TYPE);
        resp.setCharacterEncoding(UTF8);
        String requestData = AJAXServletUtil.getRequestBody(req);
        Command command = new CommandDefiner().getCommand(requestData);
        command.execute(req, resp, requestData);
    }
}
