package be.isach.samaritan;

import be.isach.samaritan.command.CommandData;
import be.isach.samaritan.command.CommandHandler;
import be.isach.samaritan.command.console.ConsoleCommand;
import be.isach.samaritan.listener.MainListener;
import be.isach.samaritan.listener.MessageLogger;
import be.isach.samaritan.listener.NewUserListener;
import me.itsghost.jdiscord.DiscordAPI;
import me.itsghost.jdiscord.DiscordBuilder;
import me.itsghost.jdiscord.Server;
import me.itsghost.jdiscord.exception.BadUsernamePasswordException;
import me.itsghost.jdiscord.exception.DiscordFailedToConnectException;
import me.itsghost.jdiscord.exception.NoLoginDetailsException;
import me.itsghost.jdiscord.talkable.Group;

import java.util.Scanner;

/**
 * Created by Sacha on 22/12/15.
 */
public class Samaritan {

    private static DiscordAPI api = null;
    private static CommandHandler commandHandler;
    private static Group botTestGroup;
    private static Server server;

    public static void main(String[] args) {

        login();

        api.getEventManager().registerListener(new MainListener(api));
        api.getEventManager().registerListener(new NewUserListener());
        api.getEventManager().registerListener(new MessageLogger());

        final Scanner scanner = new Scanner(System.in);

        new Thread() {

            @Override
            public void run() {
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                botTestGroup = api.getAvailableServers().get(0).getGroups().get(api.getAvailableServers().get(0).getGroups().size() - 1);
                server = api.getAvailableServers().get(0);
                commandHandler = new CommandHandler(botTestGroup);
                while (true) {
                    try {
                        String nextLine = scanner.nextLine();
                        CommandData commandData;
                        String label;
                        String[] args;
                        if (!commandHandler.lastConsoleCommandFinished
                                && commandHandler.lastConsoleCommand != null
                                && commandHandler.lastConsoleCommand.hasTwoUses()) {
                            commandData = commandHandler.filter(nextLine, true);
                            args = commandData.getArgs();
                            commandHandler.lastConsoleCommandFinished = true;
                            ConsoleCommand comm = commandHandler.lastConsoleCommand;
                            comm.onSecondExecute(args);
                            commandHandler.lastConsoleCommand = null;
                        } else {
                            commandData = commandHandler.filter(nextLine, false);
                            label = commandData.getLabel();
                            args = commandData.getArgs();
                            ConsoleCommand consoleCommand = commandHandler.getConsoleCommand(label);
                            if (consoleCommand == null) {
                                System.out.println("Unknown Command. 'help' for all commands.");
                                continue;
                            }

                            if (consoleCommand.hasTwoUses() && args.length == 1 && args[0].isEmpty()) {
                                commandHandler.lastConsoleCommand = consoleCommand;
                                commandHandler.lastConsoleCommandFinished = false;
                                continue;
                            }

                            if (!commandHandler.callConsole(label, args))
                                System.out.println("Unknown Command. 'help' for all commands.");
                        }
                    } catch (Exception exc) {
                        exc.printStackTrace();
                        System.out.println("Error happened or unknown command.");
                    }
                }
            }
        }.run();
    }

    public static Server getServer() {
        return server;
    }

    private static void login() {
        try {
            api = new DiscordBuilder("terhrjfe", "dfrezf").build().login();
        } catch (NoLoginDetailsException e) {
            e.printStackTrace();
        } catch (BadUsernamePasswordException e) {
            e.printStackTrace();
        } catch (DiscordFailedToConnectException e) {
            e.printStackTrace();
        }
    }

}
