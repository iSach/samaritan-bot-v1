package be.isach.samaritan.command.console.commands;

import be.isach.samaritan.command.console.ConsoleCommand;
import be.isach.samaritan.util.Helper;

/**
 * Created by Sacha on 5/01/16.
 */
public class PrintCommand extends ConsoleCommand {

    public PrintCommand() {
        super("print", true, "say");
    }

    @Override
    public void onFirstExecute(String[] args) {
        print(args);
    }

    @Override
    public void onSecondExecute(String[] args) {
        print(args);
    }

    private void print(String[] args) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String arg : args)
            stringBuilder.append(arg + " ");
        Helper.broadcast(stringBuilder.toString());
    }

}
