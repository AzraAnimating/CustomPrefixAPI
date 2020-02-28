package de.azraanimating.customprefixapi.command;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.sharding.DefaultShardManager;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;

import java.util.ArrayList;
import java.util.List;

public class CommandHandler {

    private static List<Command> commands = new ArrayList<Command>();

    public void addCommand(Command command){
        commands.add(command);
    }

    public List<Command> getCommands(){
        return commands;
    }

    public void handle(GuildMessageReceivedEvent event, String prefix){
        String message = event.getMessage().getContentRaw();

        if(message.startsWith(prefix)){

            String[] args = message.split(" ");
            String handleCommand = args[0].substring(prefix.length());

            commands.forEach(command -> {
                if(handleCommand.equalsIgnoreCase(command.getName())){
                    command.excecute(new CommandEvent(event, command, prefix));
                }
            });
        }
    }

}
