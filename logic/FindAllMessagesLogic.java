package by.galkina.game.logic;


import by.galkina.game.dao.impl.MessageDao;
import by.galkina.game.entity.Message;
import by.galkina.game.exception.DAOException;
import by.galkina.game.exception.TechnicalException;

import java.util.List;

public class FindAllMessagesLogic {

    public static List<Message> find() throws TechnicalException {
        List<Message> messages;
        try {
            messages = new MessageDao().findAll();
        } catch (DAOException e) {
            throw new TechnicalException(e);
        }
        return messages;
    }
}
