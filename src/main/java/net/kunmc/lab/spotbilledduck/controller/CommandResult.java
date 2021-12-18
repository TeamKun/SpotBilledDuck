package net.kunmc.lab.spotbilledduck.controller;

import dev.kotx.flylib.command.CommandContext;

public class CommandResult {

    private boolean isSucceed;
    private String message;

    public CommandResult(boolean isSucceed, String message) {
        this.isSucceed = isSucceed;
        this.message = message;
    }

    void sendResult(CommandContext ctx) {
        if (this.isSucceed) {
            ctx.success(this.message);
            return;
        }

        ctx.fail(this.message);
    }
}
