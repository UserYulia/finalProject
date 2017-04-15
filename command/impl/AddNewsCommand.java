package by.galkina.game.command.impl;

import by.galkina.game.command.Command;
import by.galkina.game.exception.TechnicalException;
import by.galkina.game.logic.AddNewsLogic;
import by.galkina.game.manager.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

public class AddNewsCommand implements Command {
    private static final String TITLE_RU = "title_ru";
    private static final String TITLE_EN = "title_en";
    private static final String TEXT_RU = "text_ru";
    private static final String TEXT_EN = "text_en";
    @Override
    public String execute(HttpServletRequest request) {
        String page = "";
        try {
            request.setCharacterEncoding("CP1251");
        } catch (UnsupportedEncodingException e) {
            //log
        }
        String titleRu = request.getParameter("title_ru");
        String textRu= request.getParameter("text_ru");
        String titleEn = request.getParameter("title_en");
        String textEn= request.getParameter("text_en");
        try {
            new AddNewsLogic().add(titleRu, textRu, titleEn, textEn);
        } catch (TechnicalException e) {
            //message
        }
        page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.ADMIN_OFFICE_PAGE_PATH);
        return page;
    }

}
