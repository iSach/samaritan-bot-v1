package be.isach.samaritan.command;

import java.util.Arrays;

/**
 * Created by Sacha on 5/01/16.
 */
public class CommandData {

    private String label;
    private String[] args;

    public CommandData(String label, String[] args) {
        this.label = label;
        this.args = args;
    }

    public String getLabel() {
        return label;
    }

    public String[] getArgs() {
        return args;
    }

    public boolean isEmpty() {
        return (Arrays.asList(args).isEmpty() && label.equalsIgnoreCase(""))
                || (args.length == 1 && args[0].isEmpty() && label.equalsIgnoreCase(""));
    }

    @Override
    public String toString() {
        return label + ": " + Arrays.toString(args);
    }
}
