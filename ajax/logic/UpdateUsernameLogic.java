package by.galkina.game.ajax.logic;

import by.galkina.game.ajax.util.JSONUtil;
import by.galkina.game.dao.impl.UserDao;
import by.galkina.game.entity.User;
import by.galkina.game.exception.DAOException;
import by.galkina.game.exception.LogicException;
import by.galkina.game.exception.TechnicalException;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

public class UpdateUsernameLogic {
    private static final String USERNAME = "username";
    public static void update(User user, String data, String lang) throws LogicException, TechnicalException {
        try {
            JSONObject json = JSONUtil.stringToJson(data);
            String username =((String) json.get(USERNAME)).trim();
            String email = user.getEmail();
            UserDao dao = new UserDao();
            user.setUsername(username);
            dao.changeUsername(email, username);
        } catch (ParseException e) {
            throw new LogicException();
        } catch (DAOException e) {
            throw new TechnicalException();
        }
    }
}
