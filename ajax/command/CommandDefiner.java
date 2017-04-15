package by.galkina.game.ajax.command;

import by.galkina.game.ajax.command.impl.*;
import by.galkina.game.ajax.util.JSONUtil;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.HashMap;


public class CommandDefiner {

    /** Field commands  */
    HashMap<String,Command> commands = new HashMap<String,Command>();

    /**
     * Constructor CommandDefiner creates a new CommandDefiner instance.
     */
    public CommandDefiner() {
        commands.put("showGames", new FindAllGamesCommand());
        commands.put("showUsers", new FindAllUsersCommand());
        commands.put("setBan", new SetBanCommand());
        commands.put("addGame", new AddGameCommand());
        commands.put("showMessages", new ShowMessagesCommand());
        Integer i = new Integer("10");
    }
    /**
     * Method getCommand ...
     *
     * @param requestData of type String
     *
     *
     *
     * @return Command
     * @throws java.io.IOException when
     */
    public Command getCommand(String requestData) throws IOException {
        JSONObject json = null;
        try {
            json = JSONUtil.stringToJson(requestData);
        } catch (ParseException e) {
            throw new IOException();
        }
        String action = JSONUtil.getCommand(json);
        return commands.get(action);
    }
}
