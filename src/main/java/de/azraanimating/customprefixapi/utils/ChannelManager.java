package de.azraanimating.customprefixapi.utils;

import net.dv8tion.jda.api.entities.Category;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;


public class ChannelManager {

    private Guild guild;

    public ChannelManager(Guild receivedGuild){
        guild = receivedGuild;
    }

    public TextChannel createTextChannel(String pName, String pTopic){
        TextChannel textChannel = guild.createTextChannel(pName).complete();
        textChannel.getManager().setTopic(pTopic).queue();

        return textChannel;
    }

    public Category createCategory(String pName){
        return guild.createCategory(pName).complete();
    }

    public TextChannel createTextChannelInCategory(Category category, String pName, String pTopic){
        TextChannel textChannel = category.createTextChannel(pName).complete();
        textChannel.getManager().setTopic(pTopic).queue();

        return textChannel;
    }

    public void deleteChannelByName(String pName){
        try {
            guild.getTextChannelsByName(pName, true).get(0).delete().complete();
        } catch (Exception e){}
    }


}
