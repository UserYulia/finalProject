package by.galkina.game.dao;

import by.galkina.game.entity.Message;
import by.galkina.game.exception.DAOException;

import java.util.List;

public interface IMessageDao  {
    List<Message> findAll() throws DAOException;
    boolean add(Message entity) throws DAOException;
}
