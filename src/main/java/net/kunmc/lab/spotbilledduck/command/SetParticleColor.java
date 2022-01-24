package net.kunmc.lab.spotbilledduck.command;

import dev.kotx.flylib.command.CommandContext;
import net.kunmc.lab.spotbilledduck.controller.CommandStrategy;
import org.jetbrains.annotations.NotNull;

public class SetParticleColor extends CommandBase {
    public SetParticleColor(CommandEnum commandEnum) {
        super(commandEnum);
        usage(usageBuilder -> {
            usageBuilder.textArgument("particleColor", suggestionBuilder -> {
                suggestionBuilder.suggest("red");
                suggestionBuilder.suggest("blue");
                suggestionBuilder.suggest("green");
                suggestionBuilder.suggest("white");
                suggestionBuilder.suggest("black");
                suggestionBuilder.suggest("purple");

            });
        });
    }

    @Override
    public void execute(@NotNull CommandContext ctx) {
        CommandStrategy.INSTANCE.execute(this.commandEnum, ctx);
    }
}
