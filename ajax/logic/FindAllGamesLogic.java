package by.galkina.game.ajax.logic;

import by.galkina.game.dao.impl.GameDao;
import by.galkina.game.entity.Game;
import by.galkina.game.entity.User;
import by.galkina.game.exception.DAOException;
import by.galkina.game.exception.TechnicalException;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FindAllGamesLogic {
    private static final String GAMES = "games";

    /**
     * Method find ...
     * @return String
     * @throws TechnicalException when
     */
    public static String find(User user) throws TechnicalException {
        JSONObject jsonObject = new JSONObject();
        List<Game> games = null;
        List<String> strings = new ArrayList<>();
        try {
            games = new GameDao().findByUser(user);
            for (Game game : games) {
                strings.add(game.toString());
            }
            jsonObject.put(GAMES, strings);
        } catch (DAOException e) {
            throw new TechnicalException(e);
        }
        return jsonObject.toJSONString();
    }
}
