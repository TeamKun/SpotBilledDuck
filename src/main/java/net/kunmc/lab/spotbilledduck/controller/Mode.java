package net.kunmc.lab.spotbilledduck.controller;

import dev.kotx.flylib.command.CommandContext;
import net.kunmc.lab.spotbilledduck.command.CommandEnum;
import net.kunmc.lab.spotbilledduck.game.GameMode;
import net.kunmc.lab.spotbilledduck.game.GameModeManager;
import net.kunmc.lab.spotbilledduck.game.PlayerStateManager;
import net.kunmc.lab.spotbilledduck.game.TeamManager;

import java.util.List;
import java.util.Locale;

class Mode extends BaseController {
    private final CommandEnum commandEnum = CommandEnum.mode;

    @Override
    public void execute(CommandContext ctx) {
        CommandResult result = null;
        try {
            List<String> args = ctx.getArgs();
            if (args.isEmpty()) {
                new Exception();
            }
            GameMode mode = GameMode.valueOf(args.get(0).toUpperCase(Locale.ROOT));
            result = GameModeManager.setCurrentMode(mode);
            result.sendResult(ctx);
            boolean parentValid;
            if (GameModeManager.isSoloMode()) {
                parentValid = PlayerStateManager.getParentPlayers().size() > 0;
            } else {
                parentValid = TeamManager.containsParentPlayersAtAllTeam();
            }
            if (!parentValid) {
                result = new CommandResult(false, "親プレイヤーが存在しません（チームモードの場合は全チームに親プレイヤーがいる必要があります）");
                result.sendResult(ctx);
            }
        } catch (Exception e) {
            result = new CommandResult(false, "存在しないモードです");
            result.sendResult(ctx);
        }
    }

    @Override
    public CommandEnum commandEnum() {
        return this.commandEnum;
    }
}
