package net.kunmc.lab.spotbilledduck.controller;

import dev.kotx.flylib.command.CommandContext;
import net.kunmc.lab.spotbilledduck.command.CommandEnum;

class Mode extends BaseController {
    private final CommandEnum commandEnum = CommandEnum.MODE;

    @Override
    public void execute(CommandContext ctx) {
    }

    @Override
    public CommandEnum commandEnum() {
        return this.commandEnum;
    }
}
