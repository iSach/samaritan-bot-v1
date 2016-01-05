package be.isach.samaritan.listener;

import me.itsghost.jdiscord.DiscordAPI;
import me.itsghost.jdiscord.event.EventListener;
import me.itsghost.jdiscord.events.UserChatEvent;
import me.itsghost.jdiscord.events.UserTypingEvent;

/**
 * Created by Sacha on 24/12/15.
 */
public class MainListener implements EventListener {

    private DiscordAPI api;

    public MainListener(DiscordAPI api) {
        this.api = api;
    }

    public void onUserChat(UserChatEvent e) {

    }

}
