package net.kunmc.lab.spotbilledduck.game;

import com.comphenix.protocol.PacketType;
import lombok.Getter;
import net.kunmc.lab.spotbilledduck.controller.CommandResult;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.stream.Collectors;

public class PlayerStateManager {
    // <親プレイヤーのID, 歩いた地点>
    @Getter
    private static Set<String> parentPlayers = new HashSet<>();
    @Getter
    private static Map<String, String> childPlayerPlace = new HashMap();

    public static void addChildPlayerPlace(Player player, Block block) {
        childPlayerPlace.put(player.getName(), Place.getXyzPlaceStringFromLocation(block.getLocation()));
    }

    public static boolean addParentPlayer(String playerName) {
        if (!isParentPlayer(playerName)) {
            parentPlayers.add(playerName);
            return true;
        }
        return false;
    }

    public static boolean removeParentPlayer(String playerName) {
        if (isParentPlayer(playerName)) {
            parentPlayers.remove(playerName);
            return true;
        }
        return false;
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

    public static boolean isParentPlayer(String playerName) {
        return parentPlayers.contains(playerName);
    }
}