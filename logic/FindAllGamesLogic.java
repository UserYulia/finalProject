package by.galkina.game.logic;

import by.galkina.game.dao.impl.GameDao;
import by.galkina.game.entity.Game;
import by.galkina.game.entity.User;
import by.galkina.game.exception.DAOException;
import by.galkina.game.exception.TechnicalException;

import java.util.List;

public class FindAllGamesLogic {
    private static final String GAMES = "games";

    public static List<Game> find(User user) throws TechnicalException {
        List<Game> games;
        try {
            games = new GameDao().findByUser(user);
        } catch (DAOException e) {
            throw new TechnicalException(e);
        }
        return games;
    }
}
