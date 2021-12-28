package net.kunmc.lab.spotbilledduck.game;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.*;

import static net.kunmc.lab.spotbilledduck.game.Place.getXyzPlaceStringFromLocation;

public class PlayerStateManager {
    // <親プレイヤーのID, 歩いた地点>
    private static Map<UUID, Set<String>> parentPlayers = new HashMap<>();

    public static void addParentPlayer(UUID id) {
        if (!isParentPlayer(id)) {
            parentPlayers.put(id, new HashSet<>());
        }
    }

    public static void removeParentPlayer(UUID id) {
        if (isParentPlayer(id)) {
            parentPlayers.remove(id);
        }
    }

    public static void addParentPlayerReachedPlace(UUID id, Location location) {
        // 本メソッド実行時にはidを含んでいる想定だが一応チェックを入れておく
        if (parentPlayers.containsKey(id))
            parentPlayers.get(id).add(getXyzPlaceStringFromLocation(location));
    }

   public static void updatePlayerState(Player player, Location location) {
        UUID id = player.getUniqueId();
        if (isParentPlayer(id)) {
            addParentPlayerReachedPlace(id, location);
        }
   }

   public static boolean isSafePlace(Player player, String place) {
        for (UUID id: getParentPlayersId(player)){
            if (parentPlayers.get(id).contains(place)) {
                return true;
            }
        }
        return false;
   }

   public static boolean isParentPlayer(UUID id) {
       return parentPlayers.containsKey(id);
   }

   public static Set<UUID> getParentPlayersId(Player player) {
       // 対象
       Set<UUID> parentPlayers = new HashSet<>();

       // ソロモードなら設定されているすべての親、チームモードならプレイヤーのチームの親を返すようにする
       if (!GameModeManager.isSoloMode()) {
           // プレイヤーの所属するチームメンバーを取得
           for (String targetPlayer: TeamManager.getTeamPlayers(player)){
               // 親がいるかチェック
               UUID targetPlayerId = Bukkit.getPlayer(targetPlayer).getUniqueId();
               if (parentPlayers.contains(targetPlayerId)) {
                   parentPlayers.add(targetPlayerId);
               }
           }
       }
       return parentPlayers;
   }

   public static Set<String> getParentPlayerPlace(Player player) {
        return parentPlayers.get(player.getUniqueId());
   }
}