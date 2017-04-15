package by.galkina.game.logic;


import by.galkina.game.dao.impl.MessageDao;
import by.galkina.game.entity.Message;
import by.galkina.game.exception.DAOException;
import by.galkina.game.exception.TechnicalException;

import java.util.Date;

public class SendMessageLogic {
    public void send(String text, String email) throws TechnicalException {
        try {
            new MessageDao().add(new Message(text,email, new Date()));
        } catch (DAOException e) {
            throw new TechnicalException();
        }
    }
}
