package net.kunmc.lab.spotbilledduck.controller;

import dev.kotx.flylib.command.CommandContext;
import net.kunmc.lab.spotbilledduck.command.CommandEnum;

import java.util.ArrayList;
import java.util.List;

public class CommandStrategy {

    public static final CommandStrategy INSTANCE = new CommandStrategy();

    private List<Controller> controllers = new ArrayList<>();

    private CommandStrategy() {
        this.controllers.add(new Start());
        this.controllers.add(new Stop());
        this.controllers.add(new Mode());
        this.controllers.add(new AddParent());
        this.controllers.add(new RemoveParent());
        this.controllers.add(new ShowStatus());
        this.controllers.add(new SetParticleColor());
    }

    public void execute(CommandEnum commandEnum, CommandContext ctx) {
        for (Controller controller : this.controllers) {
            if (commandEnum == controller.commandEnum()) {
                controller.execute(ctx);
                return;
            }
        }
        ctx.fail("予期せぬエラーが発生しました");
    }
}
