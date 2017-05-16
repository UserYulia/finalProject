package by.galkina.game.ajax.command;

import by.galkina.game.ajax.command.impl.*;
import by.galkina.game.ajax.util.JSONUtil;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.HashMap;


public class CommandDefiner {

    HashMap<String,Command> commands = new HashMap<>();

    public CommandDefiner() {
        commands.put("setBan", new SetBanCommand());
        commands.put("addGame", new AddGameCommand());
        commands.put("changeUsername", new UpdateUsernameCommand());
    }

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
