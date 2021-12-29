package net.kunmc.lab.spotbilledduck.controller;

import dev.kotx.flylib.command.CommandContext;
import net.kunmc.lab.spotbilledduck.command.CommandEnum;
import net.kunmc.lab.spotbilledduck.game.PlayerStateManager;

class AddParent extends BaseController {

    private final CommandEnum commandEnum = CommandEnum.addParent;

    @Override
    public void execute(CommandContext ctx) {
        CommandResult result = PlayerStateManager.addParentPlayer(ctx.getPlayer().getUniqueId());
        result.sendResult(ctx);
    }

    @Override
    public CommandEnum commandEnum() {
        return this.commandEnum;
    }
}
