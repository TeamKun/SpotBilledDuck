package net.kunmc.lab.spotbilledduck.command;

import dev.kotx.flylib.command.CommandContext;
import net.kunmc.lab.spotbilledduck.controller.CommandStrategy;
import org.jetbrains.annotations.NotNull;

public class Stop extends CommandBase {

    public Stop(CommandEnum commandEnum) {
        super(commandEnum);
    }

    @Override
    public void execute(@NotNull CommandContext ctx) {
        CommandStrategy.INSTANCE.execute(this.commandEnum, ctx);
    }
}
