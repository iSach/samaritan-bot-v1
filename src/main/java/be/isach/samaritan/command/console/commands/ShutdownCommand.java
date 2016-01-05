package be.isach.samaritan.command.console.commands;

import be.isach.samaritan.command.console.ConsoleCommand;
import be.isach.samaritan.util.Helper;

/**
 * Created by Sacha on 4/01/16.
 */
public class ShutdownCommand extends ConsoleCommand {

    public ShutdownCommand() {
        super("shutdown", false,"quit");
    }

    @Override
    public void onFirstExecute(String[] args) {
        Helper.shutdown();
    }

    @Override
    public void onSecondExecute(String[] args) {}
}
