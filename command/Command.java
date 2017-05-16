package by.galkina.game.command;

import javax.servlet.http.HttpServletRequest;


public interface Command {
    String execute(HttpServletRequest request);
}
