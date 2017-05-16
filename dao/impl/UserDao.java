package by.galkina.game.dao.impl;

import by.galkina.game.dao.IUserDao;
import by.galkina.game.entity.Gender;
import by.galkina.game.entity.Role;
import by.galkina.game.entity.User;
import by.galkina.game.exception.ConnectionPoolException;
import by.galkina.game.exception.DAOException;
import by.galkina.game.jdbc.ConnectionPool;
import by.galkina.game.jdbc.ConnectionWrapper;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class UserDao implements IUserDao {
    private static final Logger LOG = LogManager.getLogger(UserDao.class);

    private static final String FIND_ALL = "SELECT * FROM user";

    private static final String UPDATE_SCORE_AND_RATING = "UPDATE user " +
            "SET score = ?, rating = ? " +
            "WHERE userId = ?";

    private static final String INSERT_USER =
            "INSERT INTO user (email, password, role, username, gender, birthday)" +
                    " VALUES(?,?,?,?,?,?)";
    private static final String SET_PHOTO =
            "UPDATE user SET photo = ? WHERE email = ?";

    private static final String SET_BAN =
            "UPDATE user SET ban = ? WHERE email = ?";

    private static final String SET_USERNAME =
            "UPDATE user SET username = ? WHERE email=?";

    private static final String FIND_BY_ID =
            "SELECT * FROM user WHERE userId = ?";

    private static final String FIND_BY_EMAIL =
            "SELECT * FROM user WHERE email = ?";

    private static final String FIND_USER_BY_EMAIL_PASSWORD =
            "SELECT * FROM user WHERE email = ? AND password = ?";

    private static final String CHANGE_USER_PASSWORD = "UPDATE user SET password = ? WHERE userId = ?";

    public  List<User> findAll() throws DAOException {
        List<User> users = new ArrayList<>();
        ResultSet resultSet;
        try (ConnectionWrapper connectionWrapper = ConnectionPool.getInstance().takeConnection();
             PreparedStatement statement = connectionWrapper.prepareStatement(FIND_ALL)) {
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                if (Role.valueOf(resultSet.getString("role").toUpperCase()).equals(Role.USER)) {
                    Long userId = resultSet.getLong("userId");
                    String email = resultSet.getString("email");
                    BigDecimal score = resultSet.getBigDecimal("score");
                    String username = resultSet.getString("username");
                    Boolean ban = resultSet.getBoolean("ban");
                    Double rating = resultSet.getDouble("rating");
                    users.add(new User(userId, email, score, username, ban, rating));
                }
            }
        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException();
        }
        return users;
    }

    public User findById(Long id) throws DAOException {
        User user = null;
        ResultSet resultSet;
        try (ConnectionWrapper connection = ConnectionPool.getInstance().takeConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_ID)
        ) {
            statement.setLong(1, id);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                user = new User();
                user.setId(Long.parseLong(resultSet.getString("userId")));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                user.setScore(resultSet.getBigDecimal("score"));
                user.setRating(resultSet.getDouble("rating"));
                user.setPhoto(resultSet.getString("photo"));
                user.setGender(Gender.valueOf(resultSet.getString("gender").toUpperCase()));
                user.setBirthday(resultSet.getDate("birthday"));
                user.setBan(resultSet.getBoolean("ban"));
            }

        } catch (SQLException ex) {
            throw new DAOException("SQLException in DAO layer!", ex);
        } catch (ConnectionPoolException ex) {
            throw new DAOException("ConnectionPool exception!", ex);
        }
        return user;
    }

    public User findByEmail(String email) throws DAOException {
        User user = null;
        ResultSet resultSet;
        try (ConnectionWrapper connectionWrapper = ConnectionPool.getInstance().takeConnection();
             PreparedStatement statement = connectionWrapper.prepareStatement(FIND_BY_EMAIL)
        ) {
            statement.setString(1, email);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                user = new User();
                user.setId(Long.parseLong(resultSet.getString("userId")));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                user.setScore(resultSet.getBigDecimal("score"));
                user.setRating(resultSet.getDouble("rating"));
                user.setPhoto(resultSet.getString("photo"));
                user.setGender(Gender.valueOf(resultSet.getString("gender").toUpperCase()));
                user.setBirthday(resultSet.getDate("birthday"));
                user.setBan(resultSet.getBoolean("ban"));
            }

        } catch (SQLException ex) {
            throw new DAOException("SQLException in DAO layer!", ex);
        } catch (ConnectionPoolException ex) {
            throw new DAOException("ConnectionPool exception!", ex);
        }

        return user;
    }

    public boolean updateScoreAndRating(User user) throws DAOException {
        try (ConnectionWrapper connectionWrapper = ConnectionPool.getInstance().takeConnection();
             PreparedStatement statement = connectionWrapper.prepareStatement(UPDATE_SCORE_AND_RATING)
        ) {
            statement.setBigDecimal(1, user.getScore());
            statement.setDouble(2, user.getRating());
            statement.setLong(3, user.getUserId());
            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new DAOException("SQLException in DAO layer!", ex);
        } catch (ConnectionPoolException ex) {
            throw new DAOException("ConnectionPool exception!", ex);
        }
        return true;
    }

    public boolean add(User entity) throws DAOException {
        try (ConnectionWrapper connectionWrapper = ConnectionPool.getInstance().takeConnection();
             PreparedStatement statement = connectionWrapper.prepareStatement(INSERT_USER)
        ) {
            statement.setString(1, entity.getEmail());
            statement.setString(2, entity.getPassword());
            statement.setString(3, entity.getRole().toString().toLowerCase());
            statement.setString(4, entity.getUsername());
            statement.setString(5, entity.getGender().toString().toLowerCase());
            statement.setTimestamp(6, new Timestamp(entity.getBirthday().getTime()));
            statement.executeUpdate();
            LOG.info("user was added");
        } catch (SQLException ex) {
            throw new DAOException("SQLException in DAO layer!", ex);
        } catch (ConnectionPoolException ex) {
            throw new DAOException("ConnectionPool exception!", ex);
        }
        return true;
    }

    public boolean changeUserPassword(User user, String newPassword) throws DAOException {
        try (ConnectionWrapper connectionWrapper = ConnectionPool.getInstance().takeConnection();
             PreparedStatement statement = connectionWrapper.prepareStatement(CHANGE_USER_PASSWORD)
        ) {
            statement.setString(1, newPassword);
            statement.setLong(2, user.getUserId());
            statement.executeUpdate();

        } catch (SQLException ex) {
            throw new DAOException("SQLException in DAO layer!", ex);
        } catch (ConnectionPoolException ex) {
            throw new DAOException("ConnectionPool exception!", ex);
        }
        return true;
    }


    public User findByEmailAndPassword(String email, String password) throws DAOException {
        User user = null;
        ResultSet resultSet;
        try (ConnectionWrapper connection = ConnectionPool.getInstance().takeConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_USER_BY_EMAIL_PASSWORD)
        ) {
            statement.setString(1, email);
            statement.setString(2, password);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                user = new User();
                user.setId(Long.parseLong(resultSet.getString("userId")));
                user.setUsername(resultSet.getString("username"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                user.setScore(resultSet.getBigDecimal("score"));
                user.setRating(resultSet.getDouble("rating"));
                user.setPhoto(resultSet.getString("photo"));
                user.setGender(Gender.valueOf(resultSet.getString("gender").toUpperCase()));
                user.setBirthday(resultSet.getDate("birthday"));
                user.setBan(resultSet.getBoolean("ban"));
                user.setRole(Role.valueOf(resultSet.getString("role").toUpperCase()));
            }

        } catch (SQLException ex) {
            throw new DAOException("SQLException in DAO layer!", ex);
        } catch (ConnectionPoolException ex) {
            throw new DAOException("ConnectionPool exception!", ex);
        }
        return user;
    }

    public boolean changeUserPhoto(String login, String photo_path) throws DAOException {
        try (ConnectionWrapper connectionWrapper = ConnectionPool.getInstance().takeConnection();
             PreparedStatement statement = connectionWrapper.prepareStatement(SET_PHOTO)
        ) {
            statement.setString(1, photo_path);
            statement.setString(2, login);
            statement.executeUpdate();

        } catch (SQLException ex) {
            throw new DAOException("SQLException in DAO layer!", ex);
        } catch (ConnectionPoolException ex) {
            throw new DAOException("ConnectionPool exception!", ex);
        }
        return true;
    }

    public boolean changeBan(String email, Boolean ban) throws DAOException {
        try (ConnectionWrapper ConnectionWrapper = ConnectionPool.getInstance().takeConnection();
             PreparedStatement statement = ConnectionWrapper.prepareStatement(SET_BAN)
        ) {
            statement.setBoolean(1, ban);
            statement.setString(2, email);
            statement.executeUpdate();

        } catch (SQLException ex) {
            throw new DAOException("SQLException in DAO layer!", ex);
        } catch (ConnectionPoolException ex) {
            throw new DAOException("ConnectionPool exception!", ex);
        }
        return true;
    }
    public boolean changeUsername(String email, String username) throws DAOException {
        try (ConnectionWrapper connectionWrapper = ConnectionPool.getInstance().takeConnection();
             PreparedStatement statement = connectionWrapper.prepareStatement(SET_USERNAME)
        ) {
            statement.setString(1, username);
            statement.setString(2, email);
            statement.executeUpdate();

        } catch (SQLException ex) {
            throw new DAOException("SQLException in DAO layer!", ex);
        } catch (ConnectionPoolException ex) {
            throw new DAOException("ConnectionPool exception!", ex);
        }
        return true;
    }
}