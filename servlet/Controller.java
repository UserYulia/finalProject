package by.galkina.game.servlet;

import by.galkina.game.command.Command;
import by.galkina.game.command.CommandDefiner;
import by.galkina.game.jdbc.ConnectionPool;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/controller")
public class Controller extends HttpServlet {
    private static final Logger LOG = Logger.getLogger(Controller.class);
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.info("Get");
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.info("Post");
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String page;
        Command command = CommandDefiner.getInstance().getCommand(req);
        page = command.execute(req);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
        dispatcher.forward(req, resp);
    }
    @Override
    public void destroy() {
        super.destroy();
        ConnectionPool.getInstance().destroyConnections();
    }
}
