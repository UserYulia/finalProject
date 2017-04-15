package by.galkina.game.ajax.logic;

import by.galkina.game.dao.impl.UserDao;
import by.galkina.game.entity.User;
import by.galkina.game.exception.DAOException;
import by.galkina.game.exception.TechnicalException;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FindAllUsersLogic {
    private final static String USERS = "users";
    public static String find() throws TechnicalException {
        JSONObject jsonObject = new JSONObject();
        List<User> users = null;
        List<String> strings = new ArrayList<>();
        try {
            users = new UserDao().findAll();
            strings.addAll(users.stream().map(User::toString).collect(Collectors.toList()));
            jsonObject.put(USERS, strings);
        } catch (DAOException e) {
            throw new TechnicalException(e);
        }
        return jsonObject.toJSONString();
    }
}
