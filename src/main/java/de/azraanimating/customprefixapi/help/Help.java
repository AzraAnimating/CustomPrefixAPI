package de.azraanimating.customprefixapi.help;

import de.azraanimating.customprefixapi.command.Command;
import de.azraanimating.customprefixapi.command.CommandEvent;
import de.azraanimating.customprefixapi.command.CommandHandler;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;

class Help extends Command {

    String helpWord = "help";
    Color helpColor = Color.GREEN;

    public Help(){
        this.name = helpWord;
    }

    public void setHelpWord(String receivedHelpWord){
        helpWord = receivedHelpWord;
    }

    public void setHelpColor(Color receivedHelpColor){
        helpColor = receivedHelpColor;
    }

    @Override
    protected void excecute(CommandEvent event) {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder
                .setColor(helpColor)
                .setTitle("Hilfe");
        if(event.getArgs().size() == 1){
            CommandHandler.commands.forEach(command -> {
                if(command.getName().equals(event.getArgs().get(0))){
                    embedBuilder.setTitle("Hilfe " + command.getName());
                    embedBuilder.addField("Description", command.getDescription(), false);
                    embedBuilder.addField("Syntax", command.getDescription(), false);
                    event.sendEmbed(embedBuilder);
                }
            });
        }
        if(event.getArgs().size() < 1) {
            CommandHandler.commands.forEach(command -> {
                embedBuilder.addField(command.getName(), command.getDescription(), true);
            });
        }
    }

}
