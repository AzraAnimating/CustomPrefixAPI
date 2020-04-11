package de.azraanimating.customprefixapi.command;

import de.azraanimating.customprefixapi.utils.Tools;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageUpdateEvent;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandEvent {

    private GuildMessageReceivedEvent event;
    private GuildMessageUpdateEvent updateEvent;
    private Command command;
    private String prefix;

    public CommandEvent(GuildMessageReceivedEvent receivedEvent, Command receivedCommand, String receivedPrefix){
        event = receivedEvent;
        command = receivedCommand;
        prefix = receivedPrefix;
    }

    public CommandEvent(GuildMessageUpdateEvent receivedEvent, Command receivedCommand, String receivedPrefix){
        updateEvent = receivedEvent;
        command = receivedCommand;
        prefix = receivedPrefix;
    }

    //GetMethods

    public Command getCommand(){
        return command;
    }

    public Guild getGuild(){
        if(event != null) {
            return event.getGuild();
        } else {
            return updateEvent.getGuild();
        }
    }

    public TextChannel getChannel(){
        if(event != null) {
            return event.getChannel();
        } else {
            return updateEvent.getChannel();
        }
    }

    public Category getCategory() {
        return this.getChannel().getParent();
    }

    public User getUser(){
        if(event != null) {
            return event.getMember().getUser();
        } else {
            return updateEvent.getMember().getUser();
        }
    }

    public Member getMember(){
        if(event != null) {
            return event.getMember();
        } else {
            return updateEvent.getMember();
        }
    }

    public User getAuthor(){
        if(event != null) {
            return event.getAuthor();
        } else {
            return updateEvent.getAuthor();
        }
    }

    public JDA getJDA(){
        if(event != null) {
            return event.getJDA();
        } else {
            return updateEvent.getJDA();
        }
    }

    public String getMessageID(){
        if(event != null) {
            return event.getMessageId();
        } else {
            return updateEvent.getMessageId();
        }
    }

    public boolean isWebhookMessage(){
        if(event != null) {
            return event.isWebhookMessage();
        } else {
            return false;
        }
    }

    public Message getMessage(){
        if(event != null) {
            return event.getMessage();
        } else {
            return updateEvent.getMessage();
        }
    }

    public String getMessageText(){
        if(event != null) {
            return event.getMessage().getContentRaw();
        } else {
            return updateEvent.getMessage().getContentRaw();
        }
    }

    public GuildMessageReceivedEvent getMessageReceivedEvent(){
        if(event != null) {
            return event;
        } else {
            return null;
        }
    }

    public GuildMessageUpdateEvent getMessageUpdateEvent() {
        if(updateEvent != null){
            return updateEvent;
        } else {
            return null;
        }
    }

    public TextChannel getTextChannel(){
        if(event != null) {
            return event.getGuild().getTextChannelById(event.getChannel().getId());
        } else {
            return updateEvent.getGuild().getTextChannelById(event.getChannel().getId());
        }
    }

    public String getPrefix(){
        return prefix;
    }

    public boolean equals(CommandEvent commandEvent){
        if(commandEvent.getMessageReceivedEvent() != null) {
            return event.equals(commandEvent.getMessageReceivedEvent());
        } else {
            return updateEvent.equals(commandEvent.getMessageUpdateEvent());
        }
    }

    public String getArgsAsString(){
        if(!this.getArgs().isEmpty()){
            if(event != null) {
                return event.getMessage().getContentRaw().substring(command.getName().length() + prefix.length() + 1);
            } else {
                return updateEvent.getMessage().getContentRaw().substring(command.getName().length() + prefix.length() + 1);
            }
        } else {
            return "";
        }
    }

    public List<String> getArgs(){
        String arguments;
        if(event != null) {
            arguments = event.getMessage().getContentRaw().substring(command.getName().length() + prefix.length());
            if (event.getMessage().getContentRaw().startsWith(prefix + " ")) {
                arguments = event.getMessage().getContentRaw().substring(command.getName().length() + prefix.length() + 1);
            }
        } else {
            arguments = updateEvent.getMessage().getContentRaw().substring(command.getName().length() + prefix.length());
            if (updateEvent.getMessage().getContentRaw().startsWith(prefix + " ")) {
                arguments = updateEvent.getMessage().getContentRaw().substring(command.getName().length() + prefix.length() + 1);
            }
        }
        if(arguments.length() > 0) {
            arguments = arguments.substring(1);
            String[] args = arguments.split(" ");
            return Arrays.asList(args);
        } else {
            return new ArrayList<String>();
        }
    }

    public String getExecutorAvatar(){
        if(event != null) {
            return "https://cdn.discordapp.com/avatars/" + event.getMember().getId() + "/" + event.getMember().getUser().getAvatarId() + ".png?size=2048";
        } else {
            return "https://cdn.discordapp.com/avatars/" + updateEvent.getMember().getId() + "/" + updateEvent.getMember().getUser().getAvatarId() + ".png?size=2048";
        }
    }

    public List<Member> getMentions(){
        List<String> memberIDs = new ArrayList<>();
        List<Member> members = new ArrayList<>();

        this.getArgs().forEach(argument -> {
            if(argument.startsWith("<@") && argument.endsWith(">")){
                argument = argument.replace("<@", "").replace(">", "").replace("!", "");
                memberIDs.add(argument);
            }
        });

        memberIDs.forEach(userID -> {
            try {
                members.add(this.getGuild().getMemberById(userID));
            } catch (Exception e){

            }
        });

        return members;
    }

    public Tools getTools(){
        return new Tools(this);
    }

    //New Methods

    public void reply(String pMessage){
        if(event != null) {
            event.getChannel().sendMessage(pMessage).queue();
        } else {
            updateEvent.getChannel().sendMessage(pMessage).queue();
        }
    }

    public void reply(MessageEmbed messageEmbed) {
        if(event != null) {
            event.getChannel().sendMessage(messageEmbed).queue();
        } else {
            updateEvent.getChannel().sendMessage(messageEmbed).queue();
        }
    }

    public void deleteEventMessage(){
        if(event != null) {
            event.getMessage().delete().queue();
        } else {
            updateEvent.getMessage().delete().queue();
        }
    }

    public void sendSimpleEmbed(String pHeader, String pMessage, Color pColor){
        EmbedBuilder eb = new EmbedBuilder()
                .addField(pHeader, pMessage, false)
                .setColor(pColor);
        if(event != null) {
            event.getChannel().sendMessage(eb.build()).queue();
        } else {
            updateEvent.getChannel().sendMessage(eb.build()).queue();
        }
    }
}