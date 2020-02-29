package de.azraanimating.customprefixapi.utils;

import de.azraanimating.customprefixapi.command.CommandEvent;

public class Tools {

    private CommandEvent event;

    public Tools(CommandEvent receivedCommandEvent){
        event = receivedCommandEvent;
    }

    public PrivateCommunicator getPrivateCommunicator(){
        return new PrivateCommunicator(event);
    }

    public ChannelManager getChannelManager() {
        return new ChannelManager(event.getGuild());
    }

    public UserManager getUserManager() {
        return new UserManager(event.getGuild());
    }
}
