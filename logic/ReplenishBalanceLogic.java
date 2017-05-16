package by.galkina.game.logic;


import by.galkina.game.dao.impl.UserDao;
import by.galkina.game.entity.User;
import by.galkina.game.exception.DAOException;
import by.galkina.game.exception.LogicException;
import by.galkina.game.exception.TechnicalException;
import by.galkina.game.manager.MessageManager;

import java.math.BigDecimal;
import java.util.regex.Pattern;


public class ReplenishBalanceLogic {
    private static final String PARAM_SUM_VALIDATION = "[1-9][0-9]{0,5}";


    public static void replenishBalance(User user, String value, BigDecimal sum, String locale) throws TechnicalException, LogicException {
        try {
            validation(sum.toString(), locale);
            BigDecimal currentBalance;
            if(value.equals("add")){
                currentBalance = user.getScore().add(sum);
            }
            else{
                if(sum.compareTo(user.getScore())>0){
                    throw new LogicException(MessageManager.getManagerByLocale(locale).getProperty(MessageManager.NOT_ENOUGH_MONEY_MESSAGE));
                }
                currentBalance = user.getScore().subtract(sum);
            }
            user.setScore(currentBalance);
            new UserDao().updateScoreAndRating(user);
        } catch (DAOException e) {
            throw new TechnicalException("DAOException", e);
        }
    }

    private static void validation( String sum, String locale) throws LogicException {
        validationField(PARAM_SUM_VALIDATION, MessageManager.INCORRECT_SUM, sum, locale);
    }

    private static void validationField(String pattern, String errorMessage, String field, String locale) throws LogicException {
        if (!Pattern.matches(pattern, field)) {
            throw new LogicException(MessageManager.getManagerByLocale(locale).getProperty(errorMessage));
        }
    }
}

