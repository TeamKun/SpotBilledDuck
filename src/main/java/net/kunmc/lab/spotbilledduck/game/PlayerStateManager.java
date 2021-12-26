package net.kunmc.lab.spotbilledduck.game;

import javafx.scene.Parent;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.*;

import static net.kunmc.lab.spotbilledduck.game.Place.getXyzPlaceStringFromLocation;

public class PlayerStateManager {
    // 親プレイヤー
    private static List<ParentPlayer> parentPlayers = new ArrayList<>();
    // 親プレイヤー探索用のリスト
    private static Set<UUID> parentPlayersSearch = new HashSet<>();

    // 子プレイヤーが直前に歩いた地点
    private static Map<UUID, String> childPlayerPrePlace = new HashMap<>();
    // 子プレイヤーの移動カウント数
    private static Map<UUID, Integer> childPlayerMoveCount = new HashMap<>();


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
                parentPlayer.getReachedPlace().add(getXyzPlaceStringFromLocation(location));
                break;
            }
        }
    }

   public static void updatePlayerState(Player player, Location location) {
        UUID id = player.getUniqueId();
        if (isParentPlayer(id)) {
            addParentPlayerReachedPlace(id, location);
        }
    }

   public static boolean isSafePlace(Player player, String place) {
       for (ParentPlayer parentPlayer: getParentPlayers(player)) {
           if (parentPlayer.getReachedPlace().contains(place)) {
               return true;
           }
       }
       return false;
   }

   public static boolean isParentPlayer(UUID id) {
       return parentPlayersSearch.contains(id);
   }

   public static Set<ParentPlayer> getParentPlayers(Player player) {
       // 対象
       Set<ParentPlayer> parentPlayers = new HashSet<>();

       // ソロモードなら設定されているすべての親、チームモードならプレイヤーのチームの親を返すようにする
       //if (!GameModeManager.isSoloMode()) {
       //    TeamManager.getTeamPlayers(player);
       //}

       return parentPlayers;
   }
}