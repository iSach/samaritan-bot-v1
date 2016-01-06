package be.isach.samaritan.command.user;

import be.isach.samaritan.Samaritan;
import be.isach.samaritan.command.CommandData;
import be.isach.samaritan.command.CommandHandler;
import be.isach.samaritan.util.Helper;
import me.itsghost.jdiscord.event.EventListener;
import me.itsghost.jdiscord.events.UserChatEvent;
import me.itsghost.jdiscord.talkable.Group;
import me.itsghost.jdiscord.talkable.User;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by Sacha on 4/01/16.
 */
public class CommandListener implements EventListener {

    public void onCommandCall(UserChatEvent event) {
        User user = event.getUser().getUser();
        String message = event.getMsg().getMessage();
        CommandHandler commandHandler = Samaritan.getCommandHandler();

        if (!user.getUsername().equals("iSach")) {
            if (user.getUsername().equals("Loïc")) throwPunchline(event.getGroup());
            else event.getGroup().sendMessage("Sorry, I only accept commands from iSach.");
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

            commandHandler.lastUserCommand.onSecondExecute(resultQuery.getArgs(), user);
            Samaritan.commandHandler.lastUserCommandFinished = true;
            Samaritan.commandHandler.lastUserCommand = null;
            return;
        }

        if (!commandHandler.waitingForCommand
                && !(message.toLowerCase().contains("samaritan")
                && (message.toLowerCase().contains("hey")
                || message.toLowerCase().contains("hi")
                || message.toLowerCase().contains("hello")
                || message.toLowerCase().contains("please"))))
            return;

        if (resultQuery.isEmpty()) {
            event.getGroup().sendMessage("Hey " + user.getUsername() + "! What do you want?");
            commandHandler.waitingForCommand = true;
            return;
        }

        Helper.print("-----------------");
        Helper.print("Request Received.");
        Helper.print("Entry: '" + message + "'");
        Helper.print("Filtered Query: '" + resultQuery + "'");
        Helper.print("-----------------");

        commandHandler.waitingForCommand = false;

        UserCommand userCommand = commandHandler.getUserCommand(resultQuery.getLabel());

        if (userCommand == null) {
            event.getGroup().sendMessage("Sorry, I didn't understand what you said.");
            return;
        }

        if (userCommand.hasTwoUses()
                && resultQuery.getArgs().length == 1
                && resultQuery.getArgs()[0].isEmpty()) {
            Samaritan.commandHandler.lastUserCommand = userCommand;
            Samaritan.commandHandler.lastUserCommandFinished = false;
            userCommand.onExecuteNoArgs(user, event.getGroup());
            return;
        }

        if (!Samaritan.commandHandler.callConsole(resultQuery.getLabel(), resultQuery.getArgs()))
            System.out.println("Unknown Command. 'help' for all commands.");
    }

    private static List<String> punchlinesForLoic;
    private static Random random = new Random();

    static {
        punchlinesForLoic = Arrays.asList("I don't talk to noobs, sorry...", "Are you trying to talk to me?", "Shut up lol");
    }

    private void throwPunchline(Group group) {
        group.sendMessage(punchlinesForLoic.get(random.nextInt(punchlinesForLoic.size())));
    }
}