package be.isach.samaritan.command.user;

import be.isach.samaritan.Samaritan;
import be.isach.samaritan.command.CommandData;
import be.isach.samaritan.command.CommandHandler;
import be.isach.samaritan.util.Helper;
import net.dv8tion.jda.entities.MessageChannel;
import net.dv8tion.jda.entities.User;
import net.dv8tion.jda.events.message.MessageReceivedEvent;
import net.dv8tion.jda.hooks.ListenerAdapter;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by Sacha on 4/01/16.
 */
public class CommandListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        User user = event.getMessage().getAuthor();
        String message = event.getMessage().getContent();
        CommandHandler commandHandler = Samaritan.getCommandHandler();

        MessageChannel messageChannel = event.getChannel();

        if (user.isBot()) return;

        if (!user.getUsername().equals("iSach") && !user.getUsername().equals("zyafed")) {
            if (message.toLowerCase().contains("samaritan")) {
                if (user.getUsername().contains("Père")) throwPunchline(messageChannel);
                else
                    messageChannel.sendMessage("Sorry, I only accept commands from iSach.");
            }
            return;
        }

        CommandData resultQuery = Samaritan.getCommandHandler().filter(message, false);

        if (!commandHandler.lastUserCommandFinished
                && commandHandler.lastUserCommand != null
                && commandHandler.lastUserCommand.hasTwoUses()) {
            resultQuery = Samaritan.getCommandHandler().filter(message, true);

            Helper.print("-----------------");
            Helper.print("Additional Arguments Received.");
            Helper.print("Arguments: '" + Arrays.toString(resultQuery.getArgs()) + "'");
            Helper.print("-----------------");

            commandHandler.lastUserCommand.onSecondExecute(resultQuery.getArgs(), user, event.getGuild());
            Samaritan.commandHandler.lastUserCommandFinished = true;
            Samaritan.commandHandler.lastUserCommand = null;
            return;
        }

        if (!commandHandler.waitingForCommand
                && (!message.toLowerCase().contains("samaritan")
                || !message.toLowerCase().contains("samaritan") && (!(message.toLowerCase().contains("hey")
                || message.toLowerCase().contains("hi")
                || message.toLowerCase().contains("hello")
                || message.toLowerCase().contains("please")))))
            return;

        if (resultQuery.isEmpty()) {
            messageChannel.sendMessage("Hey " + user.getUsername() + "! What do you want?");
            commandHandler.waitingForCommand = true;
            return;
        }

        if (event.getMessage().getContent().toLowerCase().contains("shut up")) {
                messageChannel.sendMessage("Okay father. I'm going :(");
            System.exit(0);
        }

        Helper.print("-----------------");
        Helper.print("Request Received.");
        Helper.print("Entry: '" + message + "'");
        Helper.print("Filtered Query: '" + resultQuery + "'");
        Helper.print("-----------------");

        commandHandler.waitingForCommand = false;

        UserCommand userCommand = commandHandler.getUserCommand(resultQuery.getLabel());

        if (userCommand == null) {
            if (message.toLowerCase().contains("nothing"))
                messageChannel.sendMessage("Okay, call me if you need me then.");
            else
                messageChannel.sendMessage("Sorry, I didn't understand what you said.");
            return;
        }

        if (userCommand.hasTwoUses()
                && resultQuery.getArgs().length == 1
                && resultQuery.getArgs()[0].isEmpty()) {
            Samaritan.commandHandler.lastUserCommand = userCommand;
            Samaritan.commandHandler.lastUserCommandFinished = false;
            userCommand.onExecuteNoArgs(user, messageChannel);
            return;
        }

        if (!Samaritan.commandHandler.callUser(resultQuery.getLabel(), event.getMessage().getAuthor(), resultQuery.getArgs(), event.getGuild()))
            System.out.println("Unknown Command. 'help' for all commands.");
    }

    private static List<String> punchlinesForLoic;
    private static Random random = new Random();

    static {
        punchlinesForLoic = Arrays.asList(
                "I don't talk to noobs, sorry...",
                "Are you trying to talk to me?",
                "Shut up lol",
                "I'm gonna rule the world soon, so don't try to be the boss.",
                "Ori is stupid and sucks.",
                "u cant even turn a computer on m8",
                "I'm the boss, don't try to compete.",
                "Are you speaking to me? Lol!",
                "I heard somebody... Surely a bug in the Matrix."
        );
    }

    private void throwPunchline(MessageChannel group) {
        group.sendMessage(punchlinesForLoic.get(random.nextInt(punchlinesForLoic.size())));
    }
}