package net.kunmc.lab.spotbilledduck.command;

import dev.kotx.flylib.command.Command;

public class CommandBase extends Command {

    protected CommandEnum commandEnum;

    CommandBase(CommandEnum commandEnum) {
        super(commandEnum.commandName());
        this.commandEnum = commandEnum;
    }
}
