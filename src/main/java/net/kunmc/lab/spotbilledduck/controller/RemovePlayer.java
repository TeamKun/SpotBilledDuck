package net.kunmc.lab.spotbilledduck.controller;

import dev.kotx.flylib.command.CommandContext;
import net.kunmc.lab.spotbilledduck.command.CommandEnum;
import net.kunmc.lab.spotbilledduck.game.GameModeManager;

class RemovePlayer extends BaseController {

    private final CommandEnum commandEnum = CommandEnum.removeParent;

    @Override
    public void execute(CommandContext ctx) {
        CommandResult result = GameModeManager.stop();
        result.sendResult(ctx);
    }

    @Override
    public CommandEnum commandEnum() {
        return this.commandEnum;
    }
}
