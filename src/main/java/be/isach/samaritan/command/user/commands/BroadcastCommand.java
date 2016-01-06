package be.isach.samaritan.command.user.commands;

import be.isach.samaritan.command.user.UserCommand;
import be.isach.samaritan.util.Helper;
import me.itsghost.jdiscord.talkable.Group;
import me.itsghost.jdiscord.talkable.User;

/**
 * Created by Sacha on 6/01/16.
 */
public class BroadcastCommand extends UserCommand {

    public BroadcastCommand() {
        super("print", true, "say", "broadcast");
    }

    @Override
    public void onFirstExecute(String[] args, User user) {
        print(args);
    }

    @Override
    public void onSecondExecute(String[] args, User user) {
        print(args);
    }

    @Override
    public void onExecuteNoArgs(User user, Group group) {
        group.sendMessage("What would you like to broadcast, " + user.getUsername() + "?");
    }

    private void print(String[] args) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String arg : args)
            stringBuilder.append(arg + " ");
        Helper.broadcast(stringBuilder.toString());
    }

}
