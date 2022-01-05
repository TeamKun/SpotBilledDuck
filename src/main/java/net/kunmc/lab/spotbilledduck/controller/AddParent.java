package net.kunmc.lab.spotbilledduck.controller;

import dev.kotx.flylib.command.CommandContext;
import net.kunmc.lab.spotbilledduck.command.CommandEnum;
import net.kunmc.lab.spotbilledduck.game.PlayerStateManager;
import org.bukkit.entity.Player;

import java.util.List;

class AddParent extends BaseController {

    private final CommandEnum commandEnum = CommandEnum.addParent;

    @Override
    public void execute(CommandContext ctx) {
        try {
            for (Object arg : ((List) ctx.getTypedArgs().get(0))) {
                if (arg instanceof Player) {
                    CommandResult result = PlayerStateManager.addParentPlayer(((Player) arg).getUniqueId());
                    result.sendResult(ctx);
                }
            }
        } catch (IndexOutOfBoundsException e) {
            ctx.fail("引数が不正です");
        }
    }

    @Override
    public CommandEnum commandEnum() {
        return this.commandEnum;
    }
}
