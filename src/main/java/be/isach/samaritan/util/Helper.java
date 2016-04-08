package be.isach.samaritan.util;

import be.isach.samaritan.Samaritan;
import net.dv8tion.jda.entities.Guild;
import net.dv8tion.jda.entities.TextChannel;

/**
 * Created by Sacha on 5/01/16.
 */
public class Helper {

    public static void broadcast(Object object) {
        Guild guild = Samaritan.api.getGuilds().get(0);
        for(TextChannel textChannel : guild.getTextChannels())
            try {
                textChannel.sendMessage(object.toString());
            } catch (Exception exc) {
                continue;
            }
    }

    public static void shutdown() {
        System.out.println("Instruction successfully received. Shutting down...");
        broadcast("Command received from console to shutdown. Goodbye!");
        Samaritan.api.shutdown();
        System.exit(0);
    }

    public static void print(Object object) {
        Samaritan.logger.info(object.toString());
        System.out.println("Samaritan > " + object);
    }

}
