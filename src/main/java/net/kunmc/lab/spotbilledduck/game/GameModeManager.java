package net.kunmc.lab.spotbilledduck.game;

import lombok.Getter;
import net.kunmc.lab.spotbilledduck.controller.CommandResult;

public class GameModeManager {
    private static GameMode currentMode = GameMode.SOLO;

    @Getter
    private static boolean isRunning;

    public static CommandResult start() {
        if (isRunning) {
            return new CommandResult(false, "既に開始されています");
        }
        toggleState();
        ParticleManager.startShowParticle();
        PlayerMoveCalculator.startAdjustPosition();
        PlayerStateManager.startRemoveParentPlayerReachedPlace();
        return new CommandResult(true, "開始しました");
    }

    public static CommandResult stop() {
        if (!isRunning) {
            return new CommandResult(false, "開始されていません");
        }
        toggleState();
        ParticleManager.stopShowParticle();
        PlayerMoveCalculator.stopAdjustPosition();
        PlayerStateManager.clearPlayerState();
        return new CommandResult(true, "停止しました");
    }

    public static CommandResult setCurrentMode(GameMode mode) {
        boolean isSucceed = currentMode != mode;

        if (!isSucceed) {
            return new CommandResult(false, "すでに" + mode.name().toLowerCase() + "モードです");
        }

        currentMode = mode;
        return new CommandResult(true, mode.name().toLowerCase() + "モードに切り替えました");
    }

    public static boolean isSoloMode() {
        return currentMode.equals(GameMode.SOLO);
    }

    private static void toggleState() {
        isRunning = !isRunning;
    }
}
