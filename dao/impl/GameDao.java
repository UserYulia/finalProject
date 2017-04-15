package by.galkina.game.dao.impl;

import by.galkina.game.dao.IGameDao;
import by.galkina.game.entity.Game;
import by.galkina.game.entity.User;
import by.galkina.game.exception.ConnectionPoolException;
import by.galkina.game.exception.DAOException;
import by.galkina.game.jdbc.ConnectionPool;
import by.galkina.game.jdbc.ProxyConnection;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class GameDao implements IGameDao {
    private static final String FIND_BY_USER = "SELECT * FROM game WHERE userId=?";
    private static final String ADD_GAME = "INSERT INTO game(win, bet, time, userId) " +
            "VALUES(?,?,?,?)";

    public List<Game> findByUser(User user) throws DAOException {
        List<Game> games = new ArrayList<>();
        ResultSet resultSet;
        try (ProxyConnection ProxyConnection = ConnectionPool.getInstance().takeConnection();
             PreparedStatement statement = ProxyConnection.prepareStatement(FIND_BY_USER)) {
            statement.setLong(1, user.getUserId());
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                boolean win = resultSet.getBoolean("win");
                BigDecimal bet = resultSet.getBigDecimal("bet");
                Date date = resultSet.getDate("time");
                Long id = resultSet.getLong("gameId");
                games.add(new Game(id, user, win, bet, date));
            }
        } catch (SQLException ex) {
            throw new DAOException("SQLException in DAO layer!", ex);
        } catch (ConnectionPoolException ex) {
            throw new DAOException("ConnectionPool exception!", ex);
        }
        return games;
    }

    public boolean add(Game entity) throws DAOException {
        try (ProxyConnection proxyConnection = ConnectionPool.getInstance().takeConnection();
             PreparedStatement statement = proxyConnection.prepareStatement(ADD_GAME)
        ) {
            statement.setBoolean(1, entity.isWin());
            statement.setBigDecimal(2, entity.getBet());
            statement.setTimestamp(3, new Timestamp(entity.getDate().getTime()));
            statement.setLong(4, entity.getUser().getUserId());
            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new DAOException("SQLException in DAO layer!", ex);
        } catch (ConnectionPoolException ex) {
            throw new DAOException("ConnectionPool exception!", ex);
        }
        return true;
    }
}
