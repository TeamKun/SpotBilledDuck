package net.kunmc.lab.spotbilledduck.game;

import net.kunmc.lab.spotbilledduck.SpotBilledDuck;
import net.kunmc.lab.spotbilledduck.controller.CommandResult;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.*;
import java.util.stream.Collectors;

import static net.kunmc.lab.spotbilledduck.game.Place.getXyzPlaceStringFromLocation;

public class PlayerStateManager {
    // <親プレイヤーのID, 歩いた地点>
    private static Map<UUID, Set<String>> parentPlayers = new HashMap<>();
    private static BukkitTask  removeParentPlayerReachedPlaceTask;

    public static void startRemoveParentPlayerReachedPlace() {
        removeParentPlayerReachedPlaceTask = new BukkitRunnable() {
            @Override
            public void run() {
                if (!GameModeManager.isRunning()) return;
                // あるブロックの足元が特定ブロック（isTargetBlock, Airなど）なら位置情報を削除()
                parentPlayers.keySet().forEach(playerId -> {
                    Set<String> removeTarget = new HashSet<>();
                    for (String place : parentPlayers.get(playerId)) {
                        Block block = Place.getBlockFromPlaceString(place);
                        Block targetBlock = block.getLocation().add(0,-1,0).getBlock();
                        if (!isTargetBlock(targetBlock)) {
                            removeTarget.add(place);
                        }
                    }
                    for (String place: removeTarget) {
                        removeParentPlayerReachedPlace(playerId, place);
                    }
                });
            }
        }.runTaskTimer(SpotBilledDuck.getPlugin(), 0, 10);
    }

    public static void stopRemoveParentPlayerReachedPlace() {
        removeParentPlayerReachedPlaceTask.cancel();
        removeParentPlayerReachedPlaceTask = null;
    }


    public static CommandResult addParentPlayer(UUID id) {
        if (!isParentPlayer(id)) {
            parentPlayers.put(id, new HashSet<>());
            return new CommandResult(true, Bukkit.getPlayer(id).getName() + "を親に追加しました");
        }
        return new CommandResult(false, Bukkit.getPlayer(id).getName() + "はすでに追加されています");
    }

    public static CommandResult removeParentPlayer(UUID id) {
        if (isParentPlayer(id)) {
            parentPlayers.remove(id);
            return new CommandResult(true, Bukkit.getPlayer(id).getName() + "を親から削除しました");
        }
        return new CommandResult(false, Bukkit.getPlayer(id).getName() + "は親に存在しません");
    }

    public static void addParentPlayerReachedPlace(UUID id, Location location) {
        // 本メソッド実行時にはidを含んでいる想定だが一応チェックを入れておく
        System.out.println(location.getBlock().getType());
        if (parentPlayers.containsKey(id))
            parentPlayers.get(id).add(getXyzPlaceStringFromLocation(location));
    }

    public static void removeParentPlayerReachedPlace(UUID id, Location location) {
        // 本メソッド実行時にはidを含んでいる想定だが一応チェックを入れておく
        if (parentPlayers.containsKey(id))
            parentPlayers.get(id).remove(getXyzPlaceStringFromLocation(location));
    }

    public static void removeParentPlayerReachedPlace(UUID id, String place) {
        // 本メソッド実行時にはidを含んでいる想定だが一応チェックを入れておく
        if (parentPlayers.containsKey(id))
            parentPlayers.get(id).remove(place);
    }


    public static void updatePlayerState(Player player, Location location) {
        UUID id = player.getUniqueId();
        if (isParentPlayer(id)) {
            addParentPlayerReachedPlace(id, location);
        }
    }

    public static boolean isTargetBlock(Block block) {
        // 対象のブロックが強制移動の対象になるかを判定
        Material type = block.getType();
        return !type.equals(Material.AIR) &&
                !type.equals(Material.CAVE_AIR) &&
                !type.equals(Material.VOID_AIR) &&
                !type.equals(Material.GRASS) &&
                !type.equals(Material.TALL_GRASS);
    }

    public static boolean isParentReachedPlace(Player player, String place) {
        for (UUID id : getParentPlayersId(player)) {
            if (parentPlayers.get(id).contains(place)) {
                return true;
            }
        }
        return false;
    }

    public static List<Player> getChildPlayers(){
        return Bukkit.getOnlinePlayers().stream().filter(e -> !isParentPlayer(e.getUniqueId())).collect(Collectors.toList());
    }

    public static boolean isParentPlayer(UUID id) {
        return parentPlayers.containsKey(id);
    }

    public static Set<UUID> getParentPlayersId(Player player) {
        // 対象
        Set<UUID> parentPlayers = new HashSet<>();

        // ソロモードなら設定されているすべての親、チームモードならプレイヤーのチームの親を返すようにする
        if (GameModeManager.isSoloMode()) {
            parentPlayers.addAll(PlayerStateManager.parentPlayers.keySet());
        } else {
            // プレイヤーの所属するチームメンバーを取得
            for (String targetPlayer : TeamManager.getTeamPlayers(player)) {
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