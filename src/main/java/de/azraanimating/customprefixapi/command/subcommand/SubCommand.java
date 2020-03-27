package de.azraanimating.customprefixapi.command.subcommand;

import de.azraanimating.customprefixapi.command.Command;
import de.azraanimating.customprefixapi.command.CommandEvent;

public abstract class SubCommand{

    protected String name = "";

    protected Command parentCommand;

    protected String description = "";

    protected String syntax = "";

    public String getName(){
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getSyntax() {
        return syntax;
    }

    public Command getParentCommand() {
        return parentCommand;
    }

    protected abstract void excecute(SubCommandEvent event);

}
