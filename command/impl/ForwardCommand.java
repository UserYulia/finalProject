package by.galkina.game.command.impl;

import by.galkina.game.command.Command;
import by.galkina.game.entity.Role;
import by.galkina.game.entity.User;
import by.galkina.game.manager.ConfigurationManager;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


public class ForwardCommand implements Command {
    private static final Logger LOG = LogManager.getLogger(ForwardCommand.class);
    private static final String PARAM_LOGIN_PAGE = "toLogin";
    private static final String PARAM_REGISTRATION_PAGE = "toRegister";
    private static final String PARAM_USER_OFFICE_PAGE = "toUserOffice";
    private static  final String PARAM_GAME_PAGE = "toGame";
    private static final String PARAM_PAGE = "forward";
    private static final String USER = "user";

    @Override
    public String execute(HttpServletRequest request) {
        LOG.info("Forward command");
        String page;
        page = checkPage(request);
        return page;
    }

    private String checkPage(HttpServletRequest request) {
        String pageParam = request.getParameter(PARAM_PAGE);
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute(USER);
        switch (pageParam) {
            case PARAM_GAME_PAGE:
                return ConfigurationManager.getInstance().getProperty(
                        ConfigurationManager.GAME_PAGE_PATH);
            case PARAM_USER_OFFICE_PAGE:{
                if(user.getRole().equals(Role.ADMIN)){
                    return ConfigurationManager.getInstance().getProperty(
                            ConfigurationManager.ADMIN_OFFICE_PAGE_PATH);
                }
                else return ConfigurationManager.getInstance().getProperty(
                        ConfigurationManager.USER_OFFICE_PAGE_PATH);
            }
            case PARAM_LOGIN_PAGE:
                return ConfigurationManager.getInstance().getProperty(
                        ConfigurationManager.SIGN_IN_PAGE_PATH);
            case PARAM_REGISTRATION_PAGE:
                return ConfigurationManager.getInstance().getProperty(
                        ConfigurationManager.REGISTRATION_PAGE_PATH);
            default:
                return ConfigurationManager.getInstance().getProperty(
                        ConfigurationManager.MAIN_PAGE_PATH);
        }
    }
}
