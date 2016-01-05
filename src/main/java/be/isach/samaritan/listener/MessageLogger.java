package be.isach.samaritan.listener;

import me.itsghost.jdiscord.event.EventListener;
import me.itsghost.jdiscord.events.UserChatEvent;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Sacha on 24/12/15.
 */
public class MessageLogger implements EventListener {

    public void onUserChat(UserChatEvent event) {
        System.out.println("Logger -> " + getCurrentTime() + event.getUser().getUser().getUsername() + ": " + event.getMsg().getMessage());
    }

    private String getCurrentTime() {
        return new SimpleDateFormat("[dd-MM-YY]").format(new Date());
    }

}
