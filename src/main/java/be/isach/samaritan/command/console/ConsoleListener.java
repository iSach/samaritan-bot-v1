package be.isach.samaritan.command.console;

import be.isach.samaritan.Samaritan;
import be.isach.samaritan.command.CommandData;
import be.isach.samaritan.command.CommandHandler;
import net.dv8tion.jda.entities.MessageChannel;

import java.util.Scanner;

/**
 * Created by Sacha on 6/01/16.
 */
public class ConsoleListener extends Thread {

    private static MessageChannel botTestChannel;

    @Override
    public void run() {
        final Scanner scanner = new Scanner(System.in);

        while (Samaritan.api.getGuilds().size() < 1)
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        botTestChannel = Samaritan.api.getGuilds().get(0).getTextChannels().get(0);
//        Samaritan.server = Samaritan.api.getGuilds().get(0);
        Samaritan.commandHandler = new CommandHandler(botTestChannel);
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
