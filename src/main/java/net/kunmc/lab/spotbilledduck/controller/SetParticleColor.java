package net.kunmc.lab.spotbilledduck.controller;

import dev.kotx.flylib.command.CommandContext;
import net.kunmc.lab.spotbilledduck.command.CommandEnum;
import net.kunmc.lab.spotbilledduck.game.ParticleManager;

import java.util.List;

class SetParticleColor extends BaseController {

    private final CommandEnum commandEnum = CommandEnum.setParticleColor;

    @Override
    public void execute(CommandContext ctx) {
        CommandResult result = null;
        try {
            List<String> args = ctx.getArgs();
            if (args.isEmpty()) {
                new Exception();
            }
            String color = args.get(0);
            if (ParticleManager.setParticleColor(color)) {
                result = new CommandResult(true, "色を" + color + "に変更しました");
            } else {
                result = new CommandResult(false, "設定できない色です");
            }
        } catch (Exception e) {
            result = new CommandResult(false, "不適切な引数です");
        } finally {
            result.sendResult(ctx);
        }
    }

    @Override
    public CommandEnum commandEnum() {
        return this.commandEnum;
    }
}
