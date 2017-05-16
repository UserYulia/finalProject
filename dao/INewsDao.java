package by.galkina.game.dao;

import by.galkina.game.entity.News;
import by.galkina.game.exception.DAOException;

import java.util.List;

public interface INewsDao {
    boolean add(String titleRu, String textRu, String titleEn, String textEn) throws DAOException;
    List<News> findAll(String lang) throws DAOException;

}
