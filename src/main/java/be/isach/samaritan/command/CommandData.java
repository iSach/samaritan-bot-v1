package be.isach.samaritan.command;

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
}
