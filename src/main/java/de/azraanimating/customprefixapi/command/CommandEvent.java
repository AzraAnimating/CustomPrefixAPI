package de.azraanimating.customprefixapi.command;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

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

    public TextChannel getTextChannel(){
        return event.getGuild().getTextChannelById(event.getChannel().getId());
    }

    public List<String> getArgs(){
        String[] args = event.getMessage().getContentRaw().substring(command.name.length() + 1).split(" ");
        List<String> list = Arrays.asList(args);

        return list;
    }

    //New Methods

    public void reply(String pMessage){
        event.getChannel().sendMessage(pMessage).queue();
    }

    public void sendEmbed(EmbedBuilder embedBuilder){
        event.getChannel().sendMessage(embedBuilder.build()).queue();
    }

    public void replyCodeLine(String pMessage){
        reply("``" + pMessage + "``");
    }

    public void replyCode(String pMessage){
        reply("```" + pMessage + "```");
    }

    public void sendSimpleEmbed(String pHeader, String pMessage, Color pColor){
        EmbedBuilder eb = new EmbedBuilder()
                .addField(pHeader, pMessage, false)
                .setColor(pColor);
        event.getChannel().sendMessage(eb.build()).queue();
    }
}
