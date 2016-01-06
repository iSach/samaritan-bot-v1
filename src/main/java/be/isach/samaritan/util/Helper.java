package be.isach.samaritan.util;

import be.isach.samaritan.Samaritan;

/**
 * Created by Sacha on 5/01/16.
 */
public class Helper {

    public static void broadcast(Object object) {
        Samaritan.getServer().getGroups().forEach(server -> server.sendMessage(object.toString()));
    }

    public static void shutdown() {
        System.out.println("Instruction successfully received. Shutting down...");
//        broadcast("Command received from console to shutdown. Goodbye!");
        Samaritan.api.stop();
        System.exit(0);
    }

    public static void print(Object object) {
        Samaritan.logger.info(object.toString());
        System.out.println("Samaritan > " + object);
    }

}
