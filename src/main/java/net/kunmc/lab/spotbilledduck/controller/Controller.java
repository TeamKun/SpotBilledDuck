package net.kunmc.lab.spotbilledduck.controller;

import dev.kotx.flylib.command.CommandContext;
import net.kunmc.lab.spotbilledduck.command.CommandEnum;

public interface Controller {

    void execute(CommandContext ctx);

    CommandEnum commandEnum();
}
