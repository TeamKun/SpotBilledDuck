package net.kunmc.lab.spotbilledduck.command;

public enum CommandEnum {
    SPOTBILLEDDUCK(),
    MODE(),
    START(),
    STOP();

    public String commandName() {
        return this.name().toLowerCase();
    }
}
