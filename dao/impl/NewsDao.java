package by.galkina.game.dao.impl;

import by.galkina.game.dao.INewsDao;
import by.galkina.game.entity.News;
import by.galkina.game.exception.ConnectionPoolException;
import by.galkina.game.exception.DAOException;
import by.galkina.game.jdbc.ConnectionPool;
import by.galkina.game.jdbc.ProxyConnection;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NewsDao implements INewsDao {
    private static final Logger LOG = LogManager.getLogger(NewsDao.class);
    private static final String INSERT_NEWS =
            "INSERT INTO news (title_ru, title_en, text_ru, text_en)" +
                    " VALUES(?,?,?,?)";

    private static final String SELECT_ALL_NEWS =
            "SELECT * FROM news";

    public List<News> findAll(String lang) throws DAOException {
        List<News> news=new ArrayList<>();
        ResultSet rs;
        try (ProxyConnection proxyConnection = ConnectionPool.getInstance().takeConnection();
             PreparedStatement statement = proxyConnection.prepareStatement(SELECT_ALL_NEWS)
        ) {
            rs = statement.executeQuery();
            while (rs.next()){
                if(lang==null){
                    news.add(new News(rs.getString("title_ru"), rs.getString("text_ru")));
                }
                else if(lang.equals("en_EN")){
                    news.add(new News(rs.getString("title_en"), rs.getString("text_en")));
                }
                else{
                    news.add(new News(rs.getString("title_ru"), rs.getString("text_ru")));
                }
            }
        } catch (SQLException e) {
            throw new DAOException();
        } catch (ConnectionPoolException e) {
            LOG.fatal("ConnectionPoolException");
        }
        return news;
    }

    public boolean add(String titleRu, String textRu, String titleEn, String textEn) throws DAOException {
        try (ProxyConnection proxyConnection = ConnectionPool.getInstance().takeConnection();
             PreparedStatement statement = proxyConnection.prepareStatement(INSERT_NEWS)
        ) {
            statement.setString(1, titleRu);
            statement.setString(2, titleEn);
            statement.setString(3, textRu);
            statement.setString(4, textEn);
            statement.executeUpdate();
            LOG.info("news was added");
        } catch (SQLException ex) {
            throw new DAOException("SQLException in DAO layer!", ex);
        } catch (ConnectionPoolException ex) {
            throw new DAOException("ConnectionPool exception!", ex);
        }
        return true;
    }
}
