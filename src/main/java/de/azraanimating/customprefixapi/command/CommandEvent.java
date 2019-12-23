package de.azraanimating.customprefixapi.command;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.lang.reflect.Array;

public class CommandEvent {

    private GuildMessageReceivedEvent event;
    private Command command;

    public CommandEvent(GuildMessageReceivedEvent receivedEvent, Command receivedCommand){
        event = receivedEvent;
        command = receivedCommand;
    }

    //GetMethods

    public Command getCommand(){
        return command;
    }

    public Guild getGuild(){
        return event.getGuild();
    }

    public TextChannel getChannel(){
        return event.getChannel();
    }

    public User getUser(){
        return event.getMember().getUser();
    }

    public Member getMember(){
        return event.getMember();
    }

    public User getAuthor(){
        return event.getAuthor();
    }

    public JDA getJDA(){
        return event.getJDA();
    }

    public String getMessageID(){
        return event.getMessageId();
    }

    public boolean isWebhookMessage(){
        return event.isWebhookMessage();
    }

    public GuildMessageReceivedEvent getMessageReceivedEvent(){
        return event;
    }


    public String[] getArgs(){
        return event.getMessage().getContentRaw().substring(command.name.length() + 1).split(" ");
    }

    //New Methods

    public void reply(String pMessage){
        event.getChannel().sendMessage(pMessage).queue();
    }

    public void replyCodeLine(String pMessage){
        reply("``" + pMessage + "``");
    }

    public void replyCode(String pMessage){
        reply("```" + pMessage + "```");
    }
}
