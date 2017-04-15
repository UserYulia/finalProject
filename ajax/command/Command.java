package by.galkina.game.ajax.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface Command {
    void execute(HttpServletRequest request, HttpServletResponse response, String requestData) throws IOException;
}
