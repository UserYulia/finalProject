package by.galkina.game.dao.impl;

import by.galkina.game.dao.IMessageDao;
import by.galkina.game.entity.Message;
import by.galkina.game.entity.User;
import by.galkina.game.exception.ConnectionPoolException;
import by.galkina.game.exception.DAOException;
import by.galkina.game.jdbc.ConnectionPool;
import by.galkina.game.jdbc.ProxyConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class MessageDao implements IMessageDao {
    private static final String FIND_ALL="SELECT * FROM message";
    private static final String INSERT_MESSAGE="INSERT INTO complaint (description, time, userId_from ) VALUES(?,?,?)";

    public List<Message> findAll() throws DAOException {
        List<Message> messages = new ArrayList<>();
        ResultSet resultSet;
        try (ProxyConnection ProxyConnection = ConnectionPool.getInstance().takeConnection();
             PreparedStatement statement = ProxyConnection.prepareStatement(FIND_ALL)) {
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Date date = resultSet.getDate("time");
                Long userId = resultSet.getLong("userId_from");
                String text = resultSet.getString("text");
                User user = new UserDao().findById(userId);
                messages.add(new Message(text,user.getEmail(),date));
            }
        }
        catch (ConnectionPoolException | SQLException e) {
            throw new DAOException();
        }
        return messages;
    }

    public boolean add(Message entity) throws DAOException {
        try (ProxyConnection proxyConnection = ConnectionPool.getInstance().takeConnection();
             PreparedStatement statement = proxyConnection.prepareStatement(INSERT_MESSAGE)
        ) {
            User user = new UserDao().findByEmail(entity.getFrom());
            statement.setString(1, entity.getText());
            statement.setTimestamp(2, new java.sql.Timestamp(entity.getDate().getTime()));
            statement.setLong(3, user.getUserId());
            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new DAOException("SQLException in DAO layer!", ex);
        } catch (ConnectionPoolException ex) {
            throw new DAOException("ConnectionPool exception!", ex);
        }
        return true;
    }
}
