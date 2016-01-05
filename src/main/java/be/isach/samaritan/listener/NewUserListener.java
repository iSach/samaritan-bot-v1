package be.isach.samaritan.listener;

import me.itsghost.jdiscord.event.EventListener;
import me.itsghost.jdiscord.events.UserJoinedChat;
import me.itsghost.jdiscord.talkable.Group;

/**
 * Created by Sacha on 24/12/15.
 */
public class NewUserListener implements EventListener {

    public void onUserJoined(UserJoinedChat event) {
        for(Group group : event.getServer().getGroups())
            group.sendMessage("Welcome " + event.getGroupUser().getUser().getUsername() + "!");
    }

}
