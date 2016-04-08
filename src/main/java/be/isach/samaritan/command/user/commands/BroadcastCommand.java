package be.isach.samaritan.command.user.commands;

import be.isach.samaritan.command.user.UserCommand;
import net.dv8tion.jda.entities.Guild;
import net.dv8tion.jda.entities.MessageChannel;
import net.dv8tion.jda.entities.User;

/**
 * Created by Sacha on 6/01/16.
 */
public class BroadcastCommand extends UserCommand {

    public BroadcastCommand() {
        super("print", true, "broadcast");
    }

    @Override
    public void onFirstExecute(String[] args, User user, Guild guild) {
        print(args, guild);
    }

    @Override
    public void onSecondExecute(String[] args, User user, Guild guild) {
        print(args, guild);
    }

    @Override
    public void onExecuteNoArgs(User user, MessageChannel group) {
        group.sendMessage("What would you like to broadcast, " + user.getUsername() + "?");
    }

    private void print(String[] args, Guild guild) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String arg : args)
            stringBuilder.append(arg + " ");
        guild.getTextChannels().forEach(textChannel -> {
            try {
                textChannel.sendMessage(stringBuilder.toString());
            } catch (Exception exc) {}
        });
    }

}
