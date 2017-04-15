package by.galkina.game.ajax.logic;

import by.galkina.game.ajax.util.JSONUtil;
import by.galkina.game.dao.impl.UserDao;
import by.galkina.game.exception.DAOException;
import by.galkina.game.exception.LogicException;
import by.galkina.game.exception.TechnicalException;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

public class SetBanLogic {
    public static void set(String data, String lang) throws LogicException, TechnicalException {
        try {
            JSONObject json = JSONUtil.stringToJson(data);
            boolean ban = Boolean.parseBoolean(((String) json.get("ban")).trim());
            String email = ((String) json.get("email")).trim();
            UserDao dao = new UserDao();
            dao.changeBan(email, ban);
        } catch (ParseException e) {
            throw new LogicException();
        } catch (DAOException e) {
            throw new TechnicalException();
        }
    }
}
