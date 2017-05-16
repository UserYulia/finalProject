package by.galkina.game.command;

import by.galkina.game.command.impl.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

public class CommandDefiner {
    private static CommandDefiner instance = null;

    private HashMap<String, Command> commands = new HashMap<>();

    private CommandDefiner() {
        commands.put("changePassword", new ChangePasswordCommand());
        commands.put("login", new LoginCommand());
        commands.put("language", new LanguageCommand());
        commands.put("logout", new LogoutCommand());
        commands.put("registration", new RegistrationCommand());
        commands.put("news", new FindNewsCommand());
        commands.put("add_news", new AddNewsCommand());
        commands.put("showGames", new ShowGamesCommand());
        commands.put("sendMessage", new SendMessageCommand());
        commands.put("forward" , new ForwardCommand());
        commands.put("reduceMoney", new ReduceMoneyCommand());
        commands.put("addMoney", new AddMoneyCommand());
    }

    public Command getCommand(HttpServletRequest request) {
        String action = request.getParameter("command");
        return commands.get(action);
    }

    public static CommandDefiner getInstance() {
        if (instance == null) {
            instance = new CommandDefiner();
        }
        return instance;
    }
}