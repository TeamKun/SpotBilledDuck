package net.kunmc.lab.spotbilledduck.game;

import lombok.Getter;
import net.kunmc.lab.spotbilledduck.controller.CommandResult;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class PlayerStateManager {
    // <親プレイヤーのID, 歩いた地点>
    @Getter
    private static Set<String> parentPlayers = new HashSet<>();

    public static CommandResult addParentPlayer(String playerName) {
        if (!isParentPlayer(playerName)) {
            parentPlayers.add(playerName);
            return new CommandResult(true, playerName + "を親に追加しました");
        }
        return new CommandResult(false, playerName + "はすでに追加されています");
    }

    public static CommandResult removeParentPlayer(String playerName) {
        if (isParentPlayer(playerName)) {
            parentPlayers.remove(playerName);
            return new CommandResult(true, playerName + "を親から削除しました");
        }
        return new CommandResult(false, playerName + "は親に存在しません");
    }

    public static boolean canStand(Block block) {
        // 対象のブロックが強制移動の対象になるかを判定
        Material type = block.getType();
        return !type.equals(Material.AIR) &&
                !type.equals(Material.CAVE_AIR) &&
                !type.equals(Material.VOID_AIR) &&
                !type.equals(Material.GRASS) &&
                !type.equals(Material.TALL_GRASS);
    }

    public static List<Player> getChildPlayers() {
        return Bukkit.getOnlinePlayers().stream().filter(e -> !isParentPlayer(e.getName())).collect(Collectors.toList());
    }

    public static boolean isParentPlayer(String playerName) {
        return parentPlayers.contains(playerName);
    }
}