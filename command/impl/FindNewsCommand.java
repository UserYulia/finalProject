package by.galkina.game.command.impl;

import by.galkina.game.command.Command;
import by.galkina.game.entity.News;
import by.galkina.game.exception.LogicException;
import by.galkina.game.logic.FindNewsLogic;
import by.galkina.game.manager.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class FindNewsCommand implements Command {
    private static final String LANG = "lang";
    @Override
    public String execute(HttpServletRequest request) {
        String page="";
        List<News> news = null;
        HttpSession session = request.getSession();
        String lang = (String) session.getAttribute(LANG);
        try {
            news = new FindNewsLogic().find(lang);
            request.setAttribute("list", news);
            page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.NEWS_PAGE_PATH);
        }catch (LogicException e) {
            e.printStackTrace();
        }
        return page;
    }
}
