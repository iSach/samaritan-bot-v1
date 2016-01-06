package be.isach.samaritan.command;

import be.isach.samaritan.command.console.ConsoleCommand;
import be.isach.samaritan.command.console.commands.PrintCommand;
import be.isach.samaritan.command.console.commands.ShutdownCommand;
import be.isach.samaritan.command.user.UserCommand;
import be.isach.samaritan.command.user.commands.BroadcastCommand;
import me.itsghost.jdiscord.talkable.Group;
import me.itsghost.jdiscord.talkable.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Sacha on 4/01/16.
 */
public class CommandHandler {

    public ConsoleCommand lastConsoleCommand;
    public UserCommand lastUserCommand;
    public boolean lastConsoleCommandFinished = true, lastUserCommandFinished = true, waitingForCommand = false;
    private Group group;
    private List<Command> userCommands, consoleCommands;

    public static List<String> blacklist;

    static {
        blacklist = Arrays.asList(
                "m8",
                "mate",
                "hey",
                "hi",
                "hello",
                ",",
                "please",
                "could",
                "you",
                "can",
                "something",
                "someone",
                "somebody",
                "anything",
                "anyone",
                "anybody",
                "samaritan");
    }

    public CommandHandler(Group group) {
        this.group = group;
        this.consoleCommands = new ArrayList<>();
        this.userCommands = new ArrayList<>();

        registerConsoleCommands();
        registerUserCommands();
    }

    private void registerConsoleCommands() {
        consoleCommands.add(new ShutdownCommand());
        consoleCommands.add(new PrintCommand());
    }

    private void registerUserCommands() {
        userCommands.add(new BroadcastCommand());
    }

    public boolean callConsole(String command, String[] args) {
        ConsoleCommand consoleCommand = getConsoleCommand(command);
        if (consoleCommand == null)
            return false;

        consoleCommand.onFirstExecute(args);
        return true;
    }

    public boolean callUser(String command, User user, String[] args) {
        UserCommand userCommand = getUserCommand(command);
        if (userCommand == null)
            return false;

        userCommand.onFirstExecute(args, user);
        return true;
    }

    public ConsoleCommand getConsoleCommand(String commandString) {
        return (ConsoleCommand) getCommand(commandString, true);
    }

    public CommandData filter(String toFilter, boolean secondUse) {
        List<String> originList = new ArrayList<>(Arrays.asList(toFilter.split(" ")));

        String[] temporaryArray = new String[originList.size()];
        for (int i = 0; i < temporaryArray.length; i++)
            temporaryArray[i] = originList.get(i);

        int currentIndex = 0;
        for (String string : originList) {
            String toReplace = string.replace("!", "").replace("?", "").replace(".", "").replace(",", "");
            if (string.isEmpty() || CommandHandler.blacklist.contains(toReplace.toLowerCase()))
                temporaryArray[currentIndex] = null;
            else
                temporaryArray[currentIndex] = toReplace;
            currentIndex++;
        }

        List<String> result = new ArrayList<>(Arrays.asList(temporaryArray));

        for (Iterator<String> iterator = result.iterator(); iterator.hasNext(); ) {
            String s = iterator.next();
            if (s == null
                    || s.isEmpty())
                iterator.remove();
        }

        String commandLabel;

        if (result.isEmpty())
            return new CommandData("", new String[]{""});

        if (secondUse)
            commandLabel = "";
        else
            commandLabel = (result.get(0)).toLowerCase();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = secondUse || result.size() == 1 ? 0 : 1; i < result.size(); i++)
            stringBuilder.append(result.get(i) + " ");

        return new CommandData(commandLabel, result.size() == 1 && !secondUse ? new String[]{""} : stringBuilder.toString().split(" "));
    }

    public UserCommand getUserCommand(String command) {
        return (UserCommand) getCommand(command, false);
    }

    private Command getCommand(String c, boolean console) {
        List<Command> commands = console ? consoleCommands : userCommands;
        final Command[] commandsArray = {null};
        commands.stream().filter(command -> command.equals(c)).forEach(command -> commandsArray[0] = command);
        return commandsArray[0];
    }

}
