package net.kunmc.lab.spotbilledduck.game;

import net.kunmc.lab.spotbilledduck.SpotBilledDuck;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class PlayerMoveCalculator {
    private static BukkitTask adjustPositionTask;

    public static void startAdjustPosition() {
        adjustPositionTask = new BukkitRunnable() {
            @Override
            public void run() {
                if (!GameModeManager.isRunning()) return;
                Bukkit.getOnlinePlayers().forEach(player -> {
                    if (!shouldCheckPlayerMove(player)) return;
                    adjustPlayer(player);
                });
            }
        }.runTaskTimer(SpotBilledDuck.getPlugin(), 0, 1);
    }

    public static boolean shouldCheckPlayerMove(Player player) {
        // Playerの移動判定やポジションを保持する判定
        return ((LivingEntity)player).isOnGround() || player.isInWaterOrBubbleColumn();
    }

    public static void stopAdjustPosition() {
        adjustPositionTask.cancel();
        adjustPositionTask = null;
    }

    private static void adjustPlayer(Player player) {
        if (PlayerStateManager.isParentPlayer(player.getUniqueId())) return;

        String playerPlace = Place.getXyzPlaceStringFromLocation(player.getLocation().getBlock().getLocation());
        if (!PlayerStateManager.isParentReachedPlace(player, playerPlace)) {
            forceMovePlayerPlace(player);
        }
    }

    private static void forceMovePlayerPlace(Player player) {
        // TODO: 空中にTPされる可能性がある点を考慮する
        // Blockの中心点とPlayerの位置情報を計算して近い位置地にTPする
        Block neighborhoodBlock = getNeighborhoodBlock(player);
        // ゲーム開始時などブロックが存在しない場合は何もしない
        if (neighborhoodBlock == null) return;
        // テレポート時のPlayerの向きや立ち位置をブロックの中心にする調整
        Location teleportLocation = neighborhoodBlock.getLocation();
        teleportLocation.setPitch(player.getLocation().getPitch());
        teleportLocation.setYaw(player.getLocation().getYaw());
        teleportLocation.add(0.5,0,0.5);
        player.teleport(teleportLocation);
    }

    private static Block getNeighborhoodBlock(Player player) {
        double px = player.getLocation().getX();
        double py = player.getLocation().getY();
        double pz = player.getLocation().getZ();

        Set<String> mergedParentPlaces = new HashSet<>();
        Set<UUID> parentPlayersId = PlayerStateManager.getParentPlayersId(player);

        for (UUID parentPlayerId : parentPlayersId) {
            mergedParentPlaces.addAll(PlayerStateManager.getParentPlayerPlace(Bukkit.getPlayer(parentPlayerId)));
        }

        double minDistance = Double.MAX_VALUE;
        Block minDistanceBlock = null;
        for (String parentPlace : mergedParentPlaces) {
            Block block = Place.getBlockFromPlaceString(parentPlace);
            double bx = block.getX();
            double by = block.getY();
            double bz = block.getZ();
            double distance = Math.sqrt((px - bx) * (px - bx) + (py - by) * (py - by) + (pz - bz) * (pz - bz));
            if (minDistance > distance) {
                minDistance = distance;
                minDistanceBlock = block;
            }
        }

        return minDistanceBlock;
    }
}
