package net.kunmc.lab.spotbilledduck.command;

public enum CommandEnum {
    spotbilledduck(),
    mode(),
    start(),
    stop(),
    showStatus(),
    addParent(),
    removeParent();

    public String commandName() {
        return this.name();
    }
}
