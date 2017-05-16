package by.galkina.game.dao;


import by.galkina.game.entity.Game;
import by.galkina.game.entity.User;
import by.galkina.game.exception.DAOException;

import java.util.List;

public interface IGameDao {
    List<Game> findByUser(User user) throws DAOException;
    boolean add(Game entity) throws DAOException;
}
