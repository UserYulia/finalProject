package by.galkina.game.logic;

import by.galkina.game.dao.impl.NewsDao;
import by.galkina.game.exception.DAOException;
import by.galkina.game.exception.TechnicalException;

public class AddNewsLogic {
    public void add(String titleRu, String textRu, String titleEn, String textEn) throws TechnicalException {
        try {
            new NewsDao().add(titleRu, textRu, titleEn, textEn);
        } catch (DAOException e) {
            throw new TechnicalException();
        }
    }
}
