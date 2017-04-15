package by.galkina.game.logic;

import by.galkina.game.dao.impl.NewsDao;
import by.galkina.game.entity.News;
import by.galkina.game.exception.DAOException;
import by.galkina.game.exception.LogicException;

import java.util.List;

public class FindNewsLogic {

    public List<News> find(String lang) throws LogicException {
        List<News> news = null;
        try {
            news = new NewsDao().findAll(lang);
        } catch (DAOException e) {
            throw new LogicException();
        }
        return news;
    }
}
