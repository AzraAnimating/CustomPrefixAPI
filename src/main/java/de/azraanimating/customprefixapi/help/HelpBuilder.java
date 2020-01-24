package de.azraanimating.customprefixapi.help;

import de.azraanimating.customprefixapi.command.Command;
import de.azraanimating.customprefixapi.command.CommandEvent;
import de.azraanimating.customprefixapi.command.CommandHandler;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;

public class HelpBuilder{
    public HelpBuilder(CommandHandler commandHandler){
        commandHandler.addCommand(new Help());
    }
}
