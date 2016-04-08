package be.isach.samaritan.command;

import java.util.Arrays;

/**
 * Created by Sacha on 4/01/16.
 */
public abstract class Command {

    private String label;
    private String[] aliases;
    private boolean twoUses;

    public Command(String label, boolean twoUses, String... aliases) {
        this.label = label;
        this.aliases = aliases;
        this.twoUses = twoUses;
    }

    public boolean hasTwoUses() {
        return twoUses;
    }

    public String getLabel() {
        return label;
    }

    public String[] getAliases() {
        return aliases;
    }

    public boolean equals(String command) {
        final String finalCommand = command.toLowerCase();
        return finalCommand.equalsIgnoreCase(label)
                || Arrays.asList(aliases).contains(finalCommand);
    }
    

}
