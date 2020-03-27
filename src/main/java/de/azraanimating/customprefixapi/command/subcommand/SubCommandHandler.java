package de.azraanimating.customprefixapi.command.subcommand;

import de.azraanimating.customprefixapi.command.Command;
import de.azraanimating.customprefixapi.command.CommandEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class SubCommandHandler{
    private static List<SubCommand> subCommands = new ArrayList<SubCommand>();

    public void addCommand(SubCommand command){
        subCommands.add(command);
    }

    public List<SubCommand> getCommands(){
        return subCommands;
    }

    public SubCommand getCommandByName(String pName){
        AtomicReference<SubCommand> foundCommand = null;
        subCommands.forEach(command -> {
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

            subCommands.forEach(command -> {
                if(handleCommand.equalsIgnoreCase(command.getName())){
                    command.excecute(new SubCommandEvent(new CommandEvent(event, command.getParentCommand(), prefix.replace(" ", "")), command, prefix.replace(" ", "")));
                }
            });
        }
    }
}
