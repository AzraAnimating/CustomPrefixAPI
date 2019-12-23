package de.azraanimating.customprefixapi.privatechannel;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.PrivateChannel;
import net.dv8tion.jda.api.entities.User;

public class PrivateMessenger {

    PrivateChannel privateChannel;

    public PrivateMessenger(User user){
        privateChannel = user.openPrivateChannel().complete();
    }

    public PrivateChannel getPrivateChannel(){
        return privateChannel;
    }

    public void sendMessage(String pMessage){
        privateChannel.sendMessage(pMessage).queue();
    }

    public Message getLastMessage(){
        return privateChannel.getHistory().getMessageById(privateChannel.getLatestMessageId());
    }
}
