package be.isach.samaritan.command.user;

import be.isach.samaritan.command.Command;
import me.itsghost.jdiscord.talkable.Group;
import me.itsghost.jdiscord.talkable.User;

/**
 * Created by Sacha on 4/01/16.
 */
public abstract class UserCommand extends Command {

    public UserCommand(String label,boolean twoUses, String... aliases) {
        super(label, twoUses, aliases);
    }

    public abstract void onFirstExecute(String[] args, User executor);

    public abstract void onSecondExecute(String[] args, User executor);

    public abstract void onExecuteNoArgs(User executor, Group group);
}
