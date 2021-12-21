package net.kunmc.lab.spotbilledduck.game;

import net.kunmc.lab.spotbilledduck.SpotBilledDuck;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.Set;
import java.util.UUID;

public class PlayerMoveCalculator {
    private static BukkitTask adjustPositionTask;

    public static void adjustPosition() {
        /**
         * 腐食処理等のループメソッド
         */
        adjustPositionTask = new BukkitRunnable() {
            @Override
            public void run() {
                Bukkit.getOnlinePlayers().forEach(player -> {
                    adjustPlayer(player);
                });
            }
        }.runTaskTimer(SpotBilledDuck.getPlugin(), 0, 1);
    }

    private static void adjustPlayer(Player player) {
        if (PlayerStateManager.isParentPlayer(player.getUniqueId())) return;

        // playerの親を取得する
        Set<ParentPlayer> parentPlayers = PlayerStateManager.getParentPlayers(player);

        String playerPlace = "hogehoge";

        // playerの親の座標一覧を取得する
        for (ParentPlayer parentPlayer: parentPlayers) {
            for (String blockPlace: parentPlayer.getReachedPlace()) {
                if (playerPlace.equals(blockPlace)){
                    // playerを移動
                    calcAdjustPlayerMove(player);
                    break;
                }
            }
        }
    }

    private static void calcAdjustPlayerMove (Player player) {
        // Blockの中心点とPlayerの位置情報を計算して位置をずらす
    }
}
