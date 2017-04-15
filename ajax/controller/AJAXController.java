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
    /** Field LOG  */
    private static final Logger LOG = Logger.getLogger(AJAXController.class);

    /**
     * Method init ...
     * @throws javax.servlet.ServletException when
     */
    @Override
    public void init() throws ServletException {
    }

    /**
     * Method doGet ...
     *
     * @param req of type HttpServletRequest
     * @param resp of type HttpServletResponse
     * @throws javax.servlet.ServletException when
     * @throws java.io.IOException when
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.info("GET method");
        processRequest(req, resp);
    }

    /**
     * Method doPost ...
     *
     * @param req of type HttpServletRequest
     * @param resp of type HttpServletResponse
     * @throws javax.servlet.ServletException when
     * @throws java.io.IOException when
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.info("POST method");
        processRequest(req, resp);
    }

    /**
     * Method processRequest ...
     *
     * @param req of type HttpServletRequest
     * @param resp of type HttpServletResponse
     * @throws javax.servlet.ServletException when
     * @throws java.io.IOException when
     */
    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        String requestData = AJAXServletUtil.getRequestBody(req);
        Command command = new CommandDefiner().getCommand(requestData);
        command.execute(req, resp, requestData);
    }
}
