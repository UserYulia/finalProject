package by.galkina.game.command.impl;

import by.galkina.game.command.Command;
import by.galkina.game.manager.ConfigurationManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


public class LanguageCommand implements Command {
    private static final Logger LOG = Logger.getLogger(LanguageCommand.class);
    private static final String PARAM_LANG = "lang";

    public String execute(HttpServletRequest request) {
        LOG.info("Language command");
        String page;
        HttpSession session = request.getSession(true);
        String lang = (String) request.getParameter(PARAM_LANG);
        LOG.info("Add to session language parameter.");
        session.setAttribute(PARAM_LANG, lang);
        page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.MAIN_PAGE_PATH);
        return page;
    }
}
