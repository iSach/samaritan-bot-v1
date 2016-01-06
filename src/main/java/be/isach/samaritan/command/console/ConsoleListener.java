package be.isach.samaritan.command.console;

import be.isach.samaritan.Samaritan;
import be.isach.samaritan.command.CommandData;
import be.isach.samaritan.command.CommandHandler;
import me.itsghost.jdiscord.talkable.Group;

import java.util.Scanner;

/**
 * Created by Sacha on 6/01/16.
 */
public class ConsoleListener extends Thread {

    private static Group botTestGroup;

    @Override
    public void run() {
        final Scanner scanner = new Scanner(System.in);
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        botTestGroup = Samaritan.api.getAvailableServers().get(0).getGroups().get(Samaritan.api.getAvailableServers().get(0).getGroups().size() - 1);
        Samaritan.server = Samaritan.api.getAvailableServers().get(0);
        Samaritan.commandHandler = new CommandHandler(botTestGroup);
        while (true) {
            try {
                String nextLine = scanner.nextLine();
                CommandData commandData;
                String label;
                String[] args;
                if (!Samaritan.commandHandler.lastConsoleCommandFinished
                        && Samaritan.commandHandler.lastConsoleCommand != null
                        && Samaritan.commandHandler.lastConsoleCommand.hasTwoUses()) {
                    commandData = Samaritan.commandHandler.filter(nextLine, true);
                    args = commandData.getArgs();
                    Samaritan.commandHandler.lastConsoleCommandFinished = true;
                    ConsoleCommand comm = Samaritan.commandHandler.lastConsoleCommand;
                    comm.onSecondExecute(args);
                    Samaritan.commandHandler.lastConsoleCommand = null;
                } else {
                    commandData = Samaritan.commandHandler.filter(nextLine, false);
                    label = commandData.getLabel();
                    args = commandData.getArgs();
                    ConsoleCommand consoleCommand = Samaritan.commandHandler.getConsoleCommand(label);
                    if (consoleCommand == null) {
                        System.out.println("Unknown Command. 'help' for all commands.");
                        continue;
                    }

                    if (consoleCommand.hasTwoUses() && args.length == 1 && args[0].isEmpty()) {
                        Samaritan.commandHandler.lastConsoleCommand = consoleCommand;
                        Samaritan.commandHandler.lastConsoleCommandFinished = false;
                        continue;
                    }

                    if (!Samaritan.commandHandler.callConsole(label, args))
                        System.out.println("Unknown Command. 'help' for all commands.");
                }
            } catch (Exception exc) {
                exc.printStackTrace();
                System.out.println("Error happened or unknown command.");
            }
        }
    }
}
