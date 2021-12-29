package net.kunmc.lab.spotbilledduck.command;

import dev.kotx.flylib.command.CommandContext;
import net.kunmc.lab.spotbilledduck.controller.CommandStrategy;
import org.jetbrains.annotations.NotNull;

public class AddParent extends CommandBase {
    public AddParent(CommandEnum commandEnum) {
        super(commandEnum);
        usage(usageBuilder -> {
            usageBuilder.entityArgument("player",false, false);
        });
    }
    @Override
    public void execute(@NotNull CommandContext ctx) {
        CommandStrategy.INSTANCE.execute(this.commandEnum, ctx);
    }
}
