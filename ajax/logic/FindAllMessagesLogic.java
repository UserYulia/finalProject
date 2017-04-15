package by.galkina.game.ajax.logic;


import by.galkina.game.dao.impl.MessageDao;
import by.galkina.game.entity.Message;
import by.galkina.game.exception.DAOException;
import by.galkina.game.exception.TechnicalException;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FindAllMessagesLogic {
    private static final String MESSAGES = "messages";

    public static String find() throws TechnicalException {
        JSONObject jsonObject = new JSONObject();
        List<Message> messages = null;
        List<String> strings = new ArrayList<>();
        try {
            messages = new MessageDao().findAll();
            strings.addAll(messages.stream().map(Message::toString).collect(Collectors.toList()));
            jsonObject.put(MESSAGES, strings);
        } catch (DAOException e) {
            throw new TechnicalException(e);
        }
        return jsonObject.toJSONString();
    }
}
