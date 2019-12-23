package de.azraanimating.customprefixapi.command;

public abstract class Command {

    public String name = "";

    public String getName(){
        return name;
    }

    protected abstract void excecute(CommandEvent event, String prefix);

}
