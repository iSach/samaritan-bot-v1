package be.isach.samaritan.listener;

import be.isach.samaritan.Samaritan;
import net.dv8tion.jda.entities.User;
import net.dv8tion.jda.events.message.MessageReceivedEvent;
import net.dv8tion.jda.hooks.ListenerAdapter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Sacha on 24/12/15.
 */
public class MessageLogger extends ListenerAdapter {

    private static final DateFormat df = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS");

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        User user = event.getAuthor();
        String msg = "[MESSAGE] " + df.format(new Date()) + " - " + user.getUsername() + " -> " + event.getMessage().getContent();
        System.out.println(msg);
        Samaritan.logger.info(msg);
    }

    private String getCurrentTime() {
        return new SimpleDateFormat("[dd-MM-YY]").format(new Date());
    }

}
