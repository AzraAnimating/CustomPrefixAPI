package de.azraanimating.customprefixapi.command;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageUpdateEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class CommandHandler {

    private List<Command> commands = new ArrayList<Command>();

    public void addCommand(Command command){
        commands.add(command);
    }

    public List<Command> getCommands(){
        return commands;
    }

    public Command getCommandByName(String pName){
        AtomicReference<Command> foundCommand = null;
        commands.forEach(command -> {
            if(command.getName().equalsIgnoreCase(pName)){
                foundCommand.set(command);
            }
        });

        return foundCommand.get();
    }

    public void handle(GuildMessageReceivedEvent event, String prefix){
        String message = event.getMessage().getContentRaw();

        if(message.startsWith(prefix)){

            String handleCommand;

            if(event.getMessage().getContentRaw().startsWith(prefix + " ")){
                String[] args = message.split(" ");
                handleCommand = args[1];
            } else {
                String[] args = message.split(" ");
                handleCommand = args[0].substring(prefix.length());
            }

            commands.forEach(command -> {
                if(handleCommand.equalsIgnoreCase(command.getName())){
                    command.excecute(new CommandEvent(event, command, prefix.replace(" ", "")));
                }
            });
        }
    }

    public void handle(GuildMessageUpdateEvent event, String prefix){
        String message = event.getMessage().getContentRaw();

        if (message.startsWith(prefix)) {

            String handleCommand;

            if (event.getMessage().getContentRaw().startsWith(prefix + " ")) {
                String[] args = message.split(" ");
                handleCommand = args[1];
            } else {
                String[] args = message.split(" ");
                handleCommand = args[0].substring(prefix.length());
            }

            commands.forEach(command -> {
                if (handleCommand.equalsIgnoreCase(command.getName())) {
                    command.excecute(new CommandEvent(event, command, prefix.replace(" ", "")));
                }
            });
        }
    }

}
