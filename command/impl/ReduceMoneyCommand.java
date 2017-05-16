package by.galkina.game.command.impl;


import by.galkina.game.command.Command;
import by.galkina.game.entity.User;
import by.galkina.game.exception.LogicException;
import by.galkina.game.exception.TechnicalException;
import by.galkina.game.logic.ReplenishBalanceLogic;
import by.galkina.game.manager.ConfigurationManager;
import by.galkina.game.manager.MessageManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;

public class ReduceMoneyCommand implements Command{
    private static final Logger LOG = Logger.getLogger(ReduceMoneyCommand.class);
    private static final String USER = "user";
    private static final String TEXT = "errorMessage";
    private static final String LANG = "lang";
    private static final String SUM = "sum";
    private static final String REDUCE = "reduce";

    @Override
    public String execute(HttpServletRequest request) {
        LOG.info("Replenish balance command");
        HttpSession session = request.getSession(true);
        User user = (User) session.getAttribute(USER);
        String lang = (String) session.getAttribute(LANG);
        BigDecimal sum = new BigDecimal(request.getParameter(SUM));
        try {
            ReplenishBalanceLogic.replenishBalance(user, REDUCE, sum, lang);
        } catch (TechnicalException e) {
            LOG.error("Something has gone wrong.", e);
            request.setAttribute(TEXT, MessageManager.getManagerByLocale(lang).getProperty(MessageManager.ERROR_PAYMENT));
        } catch (LogicException e) {
            LOG.error("LogicException", e);
            request.setAttribute(TEXT, e.getMessage());
        }
        return ConfigurationManager.getInstance().getProperty(ConfigurationManager.USER_OFFICE_PAGE_PATH);
    }
}
