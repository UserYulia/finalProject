package by.galkina.game.ajax.logic;

import by.galkina.game.ajax.util.JSONUtil;
import by.galkina.game.dao.impl.UserDao;
import by.galkina.game.entity.User;
import by.galkina.game.exception.DAOException;
import by.galkina.game.exception.LogicException;
import by.galkina.game.exception.TechnicalException;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

public class SetBanLogic {
    private static final String BAN = "ban";
    private static final String EMAIL = "email";

    public static void set(String data) throws LogicException, TechnicalException {
        try {
            JSONObject json = JSONUtil.stringToJson(data);
            boolean ban = Boolean.parseBoolean(((String) json.get(BAN)).trim());
            String email = ((String) json.get(EMAIL)).trim();
            UserDao dao = new UserDao();
            User user = dao.findByEmail(email);
            user.setBan(ban);
            dao.changeBan(email, ban);
        } catch (ParseException e) {
            throw new LogicException();
        } catch (DAOException e) {
            throw new TechnicalException();
        }
    }
}
