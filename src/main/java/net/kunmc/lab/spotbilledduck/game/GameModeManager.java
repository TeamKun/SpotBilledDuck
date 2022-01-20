package net.kunmc.lab.spotbilledduck.game;

import lombok.Getter;
import net.kunmc.lab.spotbilledduck.controller.CommandResult;
import org.bukkit.craftbukkit.libs.org.apache.commons.io.FilenameUtils;

public class GameModeManager {
    private static GameMode currentMode = GameMode.SOLO;

    @Getter
    private static boolean isRunning;

    // リセットが難しいメタデータに持たせる値、start時間をメタデータに持たせることで疑似的にリセット可能にする
    @Getter
    private static String startTime;

    public static CommandResult start() {
        if (isRunning) {
            return new CommandResult(false, "既に開始されています");
        }
        boolean parentValid;
        if (GameModeManager.isSoloMode()) {
            parentValid = PlayerStateManager.getParentPlayers().size() > 0;
        } else {
            parentValid = TeamManager.containsParentPlayersAtAllTeam();
        }
        if (!parentValid) {
            return new CommandResult(false, "親プレイヤーが存在しません（チームモードの場合は全チームに親プレイヤーがいる必要があります）");
        }
        toggleState();
        startTime = Integer.toString((int) (System.currentTimeMillis() / 1000L));
        ParticleManager.startShowParticle();
        return new CommandResult(true, "開始しました");
    }

    public static CommandResult stop() {
        if (!isRunning) {
            return new CommandResult(false, "開始されていません");
        }
        toggleState();
        ParticleManager.stopShowParticle();
        PlayerStateManager.getChildPlayerPlace().clear();
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
