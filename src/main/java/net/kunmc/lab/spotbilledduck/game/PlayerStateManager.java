package net.kunmc.lab.spotbilledduck.game;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.*;

public class PlayerStateManager {
    // 親プレイヤー
    private static List<ParentPlayer> parentPlayers = new ArrayList<>();
    // 親プレイヤー探索用のリスト
    private static Set<UUID> parentPlayersSearch = new HashSet<>();

    // 子プレイヤーが直前に歩いた地点
    private static Map<UUID, String> childPlayerPrePlace = new HashMap<>();
    // 子プレイヤーの移動カウント数
    private static Map<UUID, Integer> childPlayerMoveCount = new HashMap<>();

    private static StringBuilder sb = new StringBuilder();

    public static void addParentPlayer(UUID id) {
        if (!isParentPlayer(id)) {
            parentPlayersSearch.add(id);
            parentPlayers.add(new ParentPlayer(id));
        }
    }

    public static void removeParentPlayer(UUID id) {
        if (isParentPlayer(id)) {
            parentPlayers.remove(id);
            parentPlayersSearch.remove(id);
        }
    }

    public static void addParentPlayerReachedPlace(UUID id, Location location) {
        for (ParentPlayer parentPlayer: parentPlayers) {
            if (parentPlayer.getId().equals(id)) {
                parentPlayer.getReachedPlace().add(getXzPlaceStringFromLocation(location));
                break;
            }
        }
    }

    public static void addChildPlayerPrePlace(UUID id, Location location) {
        childPlayerPrePlace.put(id, getXyzPlaceStringFromLocation(location));
    }

    private static String getXyzPlaceStringFromLocation(Location location) {
        sb.append(location.getWorld().getName());
        sb.append(" ");
        sb.append(location.getX());
        sb.append(" ");
        sb.append(location.getY());
        sb.append(" ");
        sb.append(location.getZ());
        String ret = sb.toString();
        sb.setLength(0);
        return ret;
    }

    private static String getXzPlaceStringFromLocation(Location location) {
        sb.append(location.getWorld().getName());
        sb.append(" ");
        sb.append(location.getX());
        sb.append(" ");
        sb.append(location.getZ());
        String ret = sb.toString();
        sb.setLength(0);
        return ret;
    }

    public static void updatePlayerState(Player player, Location location) {
        UUID id = player.getUniqueId();
        if (isParentPlayer(id)) {
            addParentPlayerReachedPlace(id, location);
        } else {
            addChildPlayerPrePlace(id, location);
        }
    }

    public static boolean isParentPlayer(UUID id) {
        return parentPlayersSearch.contains(id);
    }

    public static Set getParentPlayers(Player player) {
        // 対象
        Set<UUID> parentPlayers = new HashSet<>();

        // ソロモードなら設定されているすべての親、チームモードならプレイヤーのチームの親を返すようにする
        //if (!GameModeManager.isSoloMode()) {
        //    TeamManager.getTeamPlayers(player);
        //}

        return parentPlayers;
    }
}