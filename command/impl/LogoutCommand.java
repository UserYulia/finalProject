package by.galkina.game.command.impl;


import by.galkina.game.command.Command;
import by.galkina.game.manager.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;

public class LogoutCommand implements Command {

    @Override
    public String execute(HttpServletRequest request) {
        request.getSession(true).invalidate();
        return ConfigurationManager.getInstance().getProperty(ConfigurationManager.MAIN_PAGE_PATH);
    }
}
