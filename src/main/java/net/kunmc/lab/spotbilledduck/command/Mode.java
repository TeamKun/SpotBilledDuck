package net.kunmc.lab.spotbilledduck.command;

import dev.kotx.flylib.command.CommandContext;
import net.kunmc.lab.spotbilledduck.controller.CommandStrategy;
import net.kunmc.lab.spotbilledduck.game.GameMode;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public class Mode extends CommandBase {

    public Mode(@NotNull CommandEnum commandEnum) {
        super(commandEnum);
        usage(usageBuilder -> {
            usageBuilder.textArgument("mode", suggestionBuilder -> {
                for (GameMode mode : GameMode.values()) {
                    suggestionBuilder.suggest(mode.name().toLowerCase(Locale.ROOT));
                }
            });
        });
    }

    @Override
    public void execute(@NotNull CommandContext ctx) {
        CommandStrategy.INSTANCE.execute(this.commandEnum, ctx);
    }
}