package by.galkina.game.command;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Yulia on 27.02.2017.
 */
public interface Command {
    String execute(HttpServletRequest request);
}
