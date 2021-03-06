package net.kunmc.lab.spotbilledduck.controller;

import dev.kotx.flylib.command.CommandContext;
import net.kunmc.lab.spotbilledduck.command.CommandEnum;
import net.kunmc.lab.spotbilledduck.game.PlayerStateManager;

class ShowStatus extends BaseController {

    private final CommandEnum commandEnum = CommandEnum.showStatus;

    @Override
    public void execute(CommandContext ctx) {
        CommandResult result = new CommandResult(true, "親プレイヤー: " + PlayerStateManager.getParentPlayers());
        result.sendResult(ctx);
    }

    @Override
    public CommandEnum commandEnum() {
        return this.commandEnum;
    }
}
