package be.isach.samaritan;

import be.isach.samaritan.command.CommandHandler;
import be.isach.samaritan.command.console.ConsoleListener;
import be.isach.samaritan.command.user.CommandListener;
import be.isach.samaritan.listener.MainListener;
import be.isach.samaritan.listener.MessageLogger;
import be.isach.samaritan.listener.NewUserListener;
import be.isach.samaritan.util.Credentials;
import be.isach.samaritan.util.CredentialsReader;
import be.isach.samaritan.util.LogFormatter;
import me.itsghost.jdiscord.DiscordAPI;
import me.itsghost.jdiscord.DiscordBuilder;
import me.itsghost.jdiscord.Server;
import me.itsghost.jdiscord.exception.BadUsernamePasswordException;
import me.itsghost.jdiscord.exception.DiscordFailedToConnectException;
import me.itsghost.jdiscord.exception.NoLoginDetailsException;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

/**
 * Created by Sacha on 22/12/15.
 */
public class Samaritan {

    public static DiscordAPI api = null;
    public static CommandHandler commandHandler;
    public static Server server;

    private static String emailAddress, password;

    public static Logger logger;

    public static void main(String[] args) {

        String date = new SimpleDateFormat("kk-mm-ss-dd-MM-yyyy").format(new Date());

        logger = Logger.getLogger("Samaritan-" + date);
        FileHandler fh;

        try {
            String path = "logs/" + date + ".log";
            fh = new FileHandler(path);
            logger.addHandler(fh);
            fh.setFormatter(new LogFormatter());
            logger.info("Starting.");

        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        CredentialsReader credentialsReader = new CredentialsReader();
        Credentials credentials = credentialsReader.getCredentials();
        emailAddress = credentials.getEmailAddress();
        password = credentials.getPassword();

        login();

        api.getEventManager().registerListener(new MainListener(api));
        api.getEventManager().registerListener(new NewUserListener());
        api.getEventManager().registerListener(new CommandListener());
        api.getEventManager().registerListener(new MessageLogger());

        new ConsoleListener().run();
    }

    public static Server getServer() {
        return server;
    }

    public static CommandHandler getCommandHandler() {
        return commandHandler;
    }

    private static void login() {
        try {
            api = new DiscordBuilder(emailAddress, password).build().login();
        } catch (NoLoginDetailsException e) {
            e.printStackTrace();
        } catch (BadUsernamePasswordException e) {
            e.printStackTrace();
        } catch (DiscordFailedToConnectException e) {
            e.printStackTrace();
        }
    }

}
