package de.azraanimating.customprefixapi.utils;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;

import java.util.ArrayList;
import java.util.List;

public class UserManager {

    private Guild guild;

    public UserManager(Guild receivedGuild){
        guild = receivedGuild;
    }

    public List<Member> getMembers(){
        return guild.getMembers();
    }

    public List<User> getUsers(){
        List<User> users = new ArrayList<>();

        guild.getMembers().forEach(member -> {
            users.add(member.getUser());
        });

        return users;
    }

    public void addRoleToMember(Member member, Role role){
        guild.addRoleToMember(member, role).queue();
    }

    public void removeRoleFromMember(Member member, Role role){
        guild.removeRoleFromMember(member, role).queue();
    }

    public String getAvatarURL(Member member){
        return "https://cdn.discordapp.com/avatars/" + member.getId() +  "/" + member.getUser().getDefaultAvatarId() + ".png?size=2048";
    }



}
