package by.galkina.game.command.impl;

import by.galkina.game.command.Command;
import by.galkina.game.dao.impl.GameDao;
import by.galkina.game.entity.Game;
import by.galkina.game.entity.User;
import by.galkina.game.exception.DAOException;
import by.galkina.game.manager.ConfigurationManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;


public class ShowGamesCommand implements Command {
    private static final String GAMES = "games";
    private static final Logger LOG = Logger.getLogger(ShowGamesCommand.class);
    @Override
    public String execute(HttpServletRequest request) {
        String page="";
        List<Game> games;
        try {
            HttpSession session = request.getSession(true);
            games = new GameDao().findByUser((User)session.getAttribute("user"));
            request.setAttribute(GAMES, games);
            page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.USER_OFFICE_PAGE_PATH);
        } catch (DAOException e) {
            LOG.fatal("DaoException!");
        }
        return page;
    }
}
