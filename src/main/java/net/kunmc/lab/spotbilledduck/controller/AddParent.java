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
            boolean added = false;
            int cnt = 0;
            for (Object arg : ((List) ctx.getTypedArgs().get(0))) {
                if (arg instanceof Player && PlayerStateManager.addParentPlayer(((Player) arg).getName())) {
                    added = true;
                    cnt++;
                }
            }
            CommandResult result;
            if (!added) {
                result = new CommandResult(false, "プレイヤーが追加されませんでした。プレイヤー名が正しいか、追加済みでないか確認してください。");
            } else {
                result = new CommandResult(true, cnt + "名のプレイヤーを追加しました。");
            }
            result.sendResult(ctx);
        } catch (IndexOutOfBoundsException e) {
            ctx.fail("引数が不正です");
        }
    }

    @Override
    public CommandEnum commandEnum() {
        return this.commandEnum;
    }
}
