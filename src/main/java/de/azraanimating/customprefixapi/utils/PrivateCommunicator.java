package de.azraanimating.customprefixapi.utils;

import de.azraanimating.customprefixapi.command.CommandEvent;
import net.dv8tion.jda.api.entities.*;

public class PrivateCommunicator {

    private CommandEvent event;

    public PrivateCommunicator(CommandEvent receivedCommandEvent){
        event = receivedCommandEvent;
    }

    private User user = event.getUser();
    private PrivateChannel privateChannel;

    public void openPrivateChannel(){
        privateChannel = user.openPrivateChannel().complete();
    }

    public void openPrivateChannelToMentionedMember(Member mentionedMember){
        privateChannel = mentionedMember.getUser().openPrivateChannel().complete();
    }

    public void sendMessage(String pPrivateMessage){
        privateChannel.sendMessage(pPrivateMessage).queue();
    }

    public MessageHistory getMessageHistory(){
        return  privateChannel.getHistory();
    }

    public PrivateChannel getPrivateChannel(){
        return privateChannel;
    }
}
