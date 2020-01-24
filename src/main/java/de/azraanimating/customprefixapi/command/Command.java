package de.azraanimating.customprefixapi.command;

public abstract class Command {

    protected String name = "";

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

    protected abstract void excecute(CommandEvent event);

}
