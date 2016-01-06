package be.isach.samaritan.listener;

import be.isach.samaritan.Samaritan;
import me.itsghost.jdiscord.event.EventListener;
import me.itsghost.jdiscord.events.UserChatEvent;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Sacha on 24/12/15.
 */
public class MessageLogger implements EventListener {

    private static final DateFormat df = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS");

    public void onUserChat(UserChatEvent event) {
        String msg = "[MESSAGE] " + df.format(new Date()) + " - " + event.getUser().getUser().getUsername() + " -> " + event.getMsg().getMessage();
        System.out.println(msg);
        Samaritan.logger.info(msg);
    }

    private String getCurrentTime() {
        return new SimpleDateFormat("[dd-MM-YY]").format(new Date());
    }

}
