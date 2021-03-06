package net.kunmc.lab.spotbilledduck.command;

import dev.kotx.flylib.command.CommandContext;
import net.kunmc.lab.spotbilledduck.controller.CommandStrategy;
import org.jetbrains.annotations.NotNull;

public class RemoveParent extends CommandBase {
    public RemoveParent(CommandEnum commandEnum) {
        super(commandEnum);
        usage(usageBuilder -> {
            usageBuilder.entityArgument("player", true, false);
        });
    }

    @Override
    public void execute(@NotNull CommandContext ctx) {
        CommandStrategy.INSTANCE.execute(this.commandEnum, ctx);
    }
}
