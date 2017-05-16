package by.galkina.game.ajax.logic;


import by.galkina.game.ajax.util.JSONUtil;
import by.galkina.game.dao.impl.GameDao;
import by.galkina.game.dao.impl.UserDao;
import by.galkina.game.entity.Game;
import by.galkina.game.entity.User;
import by.galkina.game.exception.DAOException;
import by.galkina.game.exception.LogicException;
import by.galkina.game.exception.TechnicalException;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.math.BigDecimal;
import java.util.Date;

public class AddGameLogic {
    public static void add(User user, String data) throws LogicException, TechnicalException {
        JSONObject json;
        try {
            json = JSONUtil.stringToJson(data);
            boolean win = Boolean.parseBoolean(((String) json.get("win")).trim());
            BigDecimal bet = new BigDecimal(((String)(json.get("bet"))).trim());
            BigDecimal score = user.getScore();
            Double rating = user.getRating();
            if(win){
                user.setScore(score.add(bet));
                user.setRating(rating+bet.doubleValue()/2);
            }
            else{
                user.setScore(score.subtract(bet));
                user.setRating(rating - bet.doubleValue()/2);
            }
            GameDao dao = new GameDao();
            Game game = create(user, win, bet);
            dao.add(game);
            new UserDao().updateScoreAndRating(user);
        } catch (ParseException e) {
            throw new LogicException();
        } catch (DAOException e) {
            throw new TechnicalException();
        }
    }
    private static Game create(User user, boolean win,BigDecimal bet){
        return new Game(user,new Date(), win, bet);
    }
}
