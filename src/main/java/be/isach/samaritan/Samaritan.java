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
import net.dv8tion.jda.JDA;
import net.dv8tion.jda.JDABuilder;

import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

/**
 * Created by Sacha on 22/12/15.
 */
public class Samaritan {

    public static JDA api = null;
    public static CommandHandler commandHandler;

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

        try {
            JDABuilder jdaBuilder = new JDABuilder();
            jdaBuilder.addListener(new MainListener());
            jdaBuilder.addListener(new NewUserListener());
            jdaBuilder.addListener(new CommandListener());
            jdaBuilder.addListener(new MessageLogger());
            jdaBuilder.setEmail(emailAddress);
            jdaBuilder.setPassword(password);
            api = jdaBuilder.buildBlocking();
        } catch (LoginException e) {
            exitInvalidLogin();
        } catch (InterruptedException e) {
            exitInvalidLogin();
        }
        new ConsoleListener().run();

    }

    private static void exitInvalidLogin() {
        logger.info("Failed to log. Sorry father.");
        logger.info("Email: " + emailAddress);
        logger.info("Password: " + password);
        System.exit(0);
        return;
    }

    public static CommandHandler getCommandHandler() {
        return commandHandler;
    }

}
