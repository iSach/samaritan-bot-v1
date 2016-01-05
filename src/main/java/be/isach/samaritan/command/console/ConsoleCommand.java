package be.isach.samaritan.command.console;

import be.isach.samaritan.command.Command;

/**
 * Created by Sacha on 4/01/16.
 */
public abstract class ConsoleCommand extends Command {

    public ConsoleCommand(String label, boolean twoUses, String... aliases) {
        super(label, twoUses, aliases);
    }

    public abstract void onFirstExecute(String[] args);

    public abstract void onSecondExecute(String[] args);

}
