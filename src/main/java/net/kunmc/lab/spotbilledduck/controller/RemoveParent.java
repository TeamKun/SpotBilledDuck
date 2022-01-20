package net.kunmc.lab.spotbilledduck.controller;

import dev.kotx.flylib.command.CommandContext;
import net.kunmc.lab.spotbilledduck.command.CommandEnum;
import net.kunmc.lab.spotbilledduck.game.GameModeManager;
import net.kunmc.lab.spotbilledduck.game.PlayerStateManager;
import net.kunmc.lab.spotbilledduck.game.TeamManager;
import org.bukkit.entity.Player;

import java.util.List;

class RemoveParent extends BaseController {

    private final CommandEnum commandEnum = CommandEnum.removeParent;

    @Override
    public void execute(CommandContext ctx) {
        try {
            boolean removed = false;
            int cnt = 0;

            for (Object arg : ((List) ctx.getTypedArgs().get(0))) {
                if (arg instanceof Player){
                   if (!PlayerStateManager.removeParentPlayer(((Player) arg).getName())) continue;
                    removed = true;
                    cnt++;
                }
            }
            CommandResult result;
            if (!removed) {
                result = new CommandResult(false, "プレイヤーが削除されませんでした。プレイヤー名が正しいか、親に登録されているか確認してください。");
            } else {
                result = new CommandResult(true, cnt + "名のプレイヤーを削除しました。");
            }
            result.sendResult(ctx);

            boolean existParentPlayer = true;
            if (GameModeManager.isSoloMode()) {
                if (PlayerStateManager.getParentPlayers().size() < 1) {
                    existParentPlayer = false;
                }
            } else {
                if (!TeamManager.containsParentPlayersAtAllTeam()) {
                    existParentPlayer = false;
                }
            }
            if (!existParentPlayer && GameModeManager.isRunning()) {
                result = new CommandResult(false, "親プレイヤーが存在しません(チームの場合はすべてのチームに親プレイヤーがいる必要があります)");
                result.sendResult(ctx);
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
