package net.kunmc.lab.spotbilledduck.game;

import net.kunmc.lab.spotbilledduck.SpotBilledDuck;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashSet;
import java.util.Set;

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

        String playerPlace = Place.getXyzPlaceStringFromLocation(player.getLocation());

        // playerの親の座標一覧を取得する
        boolean moveFlag = true;
        for (ParentPlayer parentPlayer: parentPlayers) {
            if (parentPlayer.getReachedPlace().contains(playerPlace)) {
                moveFlag = false;
            }
        }
        // 移動させる必要がなければ終わり
        if (!moveFlag) return;

        // 移動させる
        adjustPlayerPlace(player);
    }

    private static void adjustPlayerPlace (Player player) {
        // Blockの中心点とPlayerの位置情報を計算して近い位置地にTPする
        Block neighborhoodBlock = getNeighborhoodBlock(player);
        player.teleport(neighborhoodBlock.getLocation());
    }

    public static String getBlockPlaceFromLocation (Location location) {
        // 計算に利用するブロックの位置情報を取得する
        return Place.getXyzPlaceStringFromLocation(location);
    }

    private static Block getNeighborhoodBlock(Player player) {
        double px = player.getLocation().getX();
        double py = player.getLocation().getY();
        double pz = player.getLocation().getZ();

        Set<String> mergedParentPlaces = new HashSet<>();
        Set<ParentPlayer> parentPlayers = PlayerStateManager.getParentPlayers(player);

        for (ParentPlayer parentPlayer: parentPlayers) {
            mergedParentPlaces.addAll(parentPlayer.getReachedPlace());
        }

        double minDistance = Double.MAX_VALUE;
        Block minDistanceBlock = null;
        for (String parentPlace: mergedParentPlaces) {
            Block block = Place.getBlockFromPlaceString(parentPlace);
            double bx = block.getX();
            double by = block.getY();
            double bz = block.getZ();
            double distance = Math.sqrt((px-bx)*(px-bx)+(py-by)*(py-by)+(pz-bz)*(pz-bz));
            if (minDistance > distance) {
                minDistance = distance;
                minDistanceBlock = block;
            }
        }

        return minDistanceBlock;
    }
}
