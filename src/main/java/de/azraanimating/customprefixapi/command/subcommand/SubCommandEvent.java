package de.azraanimating.customprefixapi.command.subcommand;

import de.azraanimating.customprefixapi.command.Command;
import de.azraanimating.customprefixapi.command.CommandEvent;
import de.azraanimating.customprefixapi.utils.Tools;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SubCommandEvent {


    private CommandEvent event;
    private SubCommand command;
    private String prefix;

    public SubCommandEvent(CommandEvent receivedEvent, SubCommand receivedCommand, String receivedPrefix){
        event = receivedEvent;
        command = receivedCommand;
        prefix = receivedPrefix;
    }

    //GetMethods

    public SubCommand getSubCommand(){
        return command;
    }

    public Guild getGuild(){
        return event.getGuild();
    }

    public TextChannel getChannel(){
        return event.getChannel();
    }

    public Category getCategory() {
        return this.getChannel().getParent();
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
        return event.getMessageID();
    }

    public boolean isWebhookMessage(){
        return event.isWebhookMessage();
    }

    public Message getMessage(){
        return event.getMessage();
    }

    public String getMessageText(){
        return event.getMessage().getContentRaw();
    }

    public GuildMessageReceivedEvent getMessageReceivedEvent(){
        return event.getMessageReceivedEvent();
    }

    public TextChannel getTextChannel(){
        return event.getGuild().getTextChannelById(event.getChannel().getId());
    }

    public String getPrefix(){
        return prefix;
    }

    public boolean equals(CommandEvent commandEvent){
        return event.equals(commandEvent.getMessageReceivedEvent());
    }


    public String getArgsAsString(){
        if(!this.getArgs().isEmpty()){
            return event.getMessage().getContentRaw().substring(command.getName().length() + prefix.length() + 1);
        } else {
            return "";
        }
    }

    public List<String> getArgs(){
        String arguments = event.getMessage().getContentRaw().substring(command.getName().length() + prefix.length());
        if(event.getMessage().getContentRaw().startsWith(prefix + " ")){
            arguments = event.getMessage().getContentRaw().substring(command.getName().length() + prefix.length() + 1);
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
        return "https://cdn.discordapp.com/avatars/" + event.getMember().getId() +  "/" + event.getMember().getUser().getAvatarId() + ".png?size=2048";
    }

    public List<Member> getMentions(){
        List<String> memberIDs = new ArrayList<>();
        List<Member> members = new ArrayList<>();

        this.getArgs().forEach(argument -> {
            if(argument.startsWith("<@!") && argument.endsWith(">")){
                argument = argument.replace("<@!", "").replace(">", "");
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
        return new Tools(event);
    }

    //New Methods

    public void reply(String pMessage){
        event.getChannel().sendMessage(pMessage).queue();
    }

    public void reply(MessageEmbed messageEmbed) {
        event.getChannel().sendMessage(messageEmbed).queue();
    }

    public void replyCodeLine(String pMessage){
        reply("``" + pMessage + "``");
    }

    public void replyCode(String pMessage){
        reply("```" + pMessage + "```");
    }

    public void deleteEventMessage(){
        event.getMessage().delete().queue();
    }

    public void sendSimpleEmbed(String pHeader, String pMessage, Color pColor){
        EmbedBuilder eb = new EmbedBuilder()
                .addField(pHeader, pMessage, false)
                .setColor(pColor);
        event.getChannel().sendMessage(eb.build()).queue();
    }

    public Command getParentCommand(){
        return event.getCommand();
    }

    public String getParentCommandName(){
        return event.getCommand().getName();
    }

    public CommandEvent getParentCommandEvent(){
        return event;
    }
}
