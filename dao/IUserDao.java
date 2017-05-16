package by.galkina.game.dao;

import by.galkina.game.entity.User;
import by.galkina.game.exception.DAOException;

import java.util.List;

public interface IUserDao{
    List<User> findAll() throws DAOException;
    User findById(Long id) throws DAOException;
    User findByEmail(String email) throws DAOException;
    boolean updateScoreAndRating(User user) throws DAOException;
    boolean add(User entity) throws DAOException;
    boolean changeUserPassword(User user, String newPassword) throws DAOException;
    User findByEmailAndPassword(String email, String password) throws DAOException;
    boolean changeUserPhoto(String login, String photo_path) throws DAOException;
    boolean changeBan(String email, Boolean ban) throws DAOException;
    boolean changeUsername(String email, String username) throws DAOException;
}
