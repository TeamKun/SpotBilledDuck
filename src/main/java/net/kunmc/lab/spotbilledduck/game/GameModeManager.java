package net.kunmc.lab.spotbilledduck.game;

import lombok.Getter;
import lombok.Setter;
import net.kunmc.lab.spotbilledduck.controller.CommandResult;

public class GameModeManager {
    @Getter
    @Setter
    private static GameMode currentMode = GameMode.SOLO;

    @Getter
    private static boolean isRunning;

    public static CommandResult start() {
        if (isRunning) {
            return new CommandResult(false, "既に開始されています");
        }
        toggleState();
        return new CommandResult(true, "開始しました");
    }

    public static CommandResult stop() {
        if (!isRunning) {
            return new CommandResult(false, "開始されていません");
        }
        toggleState();
        return new CommandResult(true, "停止しました");
    }

    private static void toggleState() {
        isRunning = !isRunning;
    }
}
