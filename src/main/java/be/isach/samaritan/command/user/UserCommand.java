package be.isach.samaritan.command.user;

import be.isach.samaritan.command.Command;
import net.dv8tion.jda.entities.Guild;
import net.dv8tion.jda.entities.MessageChannel;
import net.dv8tion.jda.entities.User;

/**
 * Created by Sacha on 4/01/16.
 */
public abstract class UserCommand extends Command {

    public UserCommand(String label,boolean twoUses, String... aliases) {
        super(label, twoUses, aliases);
    }

    public abstract void onFirstExecute(String[] args, User executor, Guild guild);

    public abstract void onSecondExecute(String[] args, User executor, Guild guild);

    public abstract void onExecuteNoArgs(User executor, MessageChannel group);
}
